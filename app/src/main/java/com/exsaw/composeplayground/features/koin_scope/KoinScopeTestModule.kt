package com.exsaw.composeplayground.features.koin_scope

import org.koin.dsl.module

// single factory scope
val koinScopeTestModule = module {
    scope<KoinScopeTestClass>{
        scoped { KoinScopedClass() }
    }
}