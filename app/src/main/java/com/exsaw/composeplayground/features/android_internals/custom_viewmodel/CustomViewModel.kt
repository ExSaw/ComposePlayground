package com.exsaw.composeplayground.features.android_internals.custom_viewmodel

import com.exsaw.composeplayground.tool.logUnlimited
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class CustomViewModel {

    protected val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    open fun onCleared() {
        viewModelScope.cancel()
    }
}

class CustomViewModelStore {
    private val viewModels = HashMap<String, CustomViewModel>()

    fun <T: CustomViewModel> put(viewModel: T) {
        viewModels[viewModel::class.java.simpleName] = viewModel
    }

    fun <T: CustomViewModel> get(clazz: Class<T>): T? {
        return viewModels[clazz.simpleName] as? T
    }

    fun clear() {
        viewModels.values.forEach {
            it.onCleared()
        }
        viewModels.clear()
    }
}

interface ICustomViewModelFactory {
    fun <T: CustomViewModel> create(modelClass: Class<T>): T
}

object DefaultCustomViewModelFactory: ICustomViewModelFactory {
    override fun <T : CustomViewModel> create(modelClass: Class<T>): T {
        return modelClass.getDeclaredConstructor().newInstance()
    }
}

class CustomViewModelProvider(
    private val store: CustomViewModelStore,
    private val factory: ICustomViewModelFactory = DefaultCustomViewModelFactory
) {
    fun <T: CustomViewModel> get(modelClass: Class<T>): T {
        val existing = store.get(modelClass)
        if(existing != null && modelClass.isInstance(existing)) {
            return existing
        }
        val customViewModel = factory.create(modelClass)
        store.put(customViewModel)
        return customViewModel
    }
}


// example
class MainCustomViewModel(
    private val initialCounter: Int,
): CustomViewModel() {
    init {
        logUnlimited("---MainCustomViewModel->INIT->${this.hashCode()}")
    }

    private val _counterState = MutableStateFlow(initialCounter)
    val counterState = _counterState.asStateFlow()

    fun increaseCounter() {
        _counterState.update { it + 1 }
    }

    class Factory(
        private val initialCounter: Int,
    ): ICustomViewModelFactory {
        override fun <T : CustomViewModel> create(modelClass: Class<T>): T {
            return MainCustomViewModel(initialCounter) as T
        }
    }
}