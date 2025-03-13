package com.exsaw.composeplayground.features.koin_scope

import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.inject
import org.koin.core.scope.Scope

class KoinScopeTestClass: KoinScopeComponent {

    override val scope: Scope by lazy { createScope(this) }
   // val scope2 = getKoin().createScope<KoinScopeTestClass>()

    val testScopedClass: KoinScopedClass by inject()

    fun close() {
        scope.close()
    }
}