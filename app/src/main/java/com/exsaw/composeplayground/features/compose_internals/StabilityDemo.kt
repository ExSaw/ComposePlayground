package com.exsaw.composeplayground.features.compose_internals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exsaw.composeplayground.di.mainModule
import com.exsaw.composeplayground.tool.logUnlimited
import com.exsaw.composeplayground.tool.onDebouncedClick
import com.exsaw.composeplayground.ui.theme.ComposePlaygroundTheme
import org.koin.compose.KoinApplication
import java.util.UUID
import kotlin.random.Random

interface ICustomInterface

class CustomInterfaceImpl: ICustomInterface

// Stable: strings, primitives, lambdas,
//@Stable // promise to compose compiler that
data class Person(
    val name: String = "null",
    val age: Int = 0,
    val prim: Int = 0,
    val customInterface: ICustomInterface = CustomInterfaceImpl(),
    val action: () -> Unit = { logUnlimited("---Person->ACTION->DEFAULT") },
    val list: List<Int> = emptyList() // unstable
)

@Composable
fun StabilityDemo(
    modifier: Modifier = Modifier
) {
    val personDataState = remember {
        mutableStateOf(Person())
    }
    Column(
        modifier = modifier
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StabilityChildDemo(
            personData = { personDataState.value },
        )
        Text(
            text = "STABLE TEXT",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(
            modifier = Modifier,
            onClick = onDebouncedClick {
                personDataState.value = personDataState.value.copy()
            }
        ) {
            Text("Shuffle NO CHANGE")
        }
        Button(
            onClick = onDebouncedClick {
                personDataState.value = personDataState.value.copy(
                    name = UUID.randomUUID().toString(),
                    age = Random.nextInt(1,100)
                )
            }
        ) {
            Text("Shuffle WITH CHANGE NAME AND AGE")
        }
        Button(
            onClick = onDebouncedClick {
                personDataState.value = personDataState.value.copy(
                    list = listOf(Random.nextInt())
                )
            }
        ) {
            Text("Shuffle WITH CHANGE LIST")
        }
        Button(
            onClick = onDebouncedClick {
                personDataState.value = personDataState.value.copy(
                    list = emptyList()
                )
            }
        ) {
            Text("Shuffle WITH CHANGE LIST TO EMPTY")
        }
        Button(
            onClick = onDebouncedClick {
                personDataState.value = personDataState.value.copy(
                    action = {
                        logUnlimited("---Person->ACTION->CUSTOM")
                    }
                )
            }
        ) {
            Text("Shuffle WITH CHANGE ACTION")
        }
        Button(
            onClick = onDebouncedClick {
                personDataState.value = personDataState.value.copy(
                    customInterface = CustomInterfaceImpl()
                )
            }
        ) {
            Text("Shuffle WITH CHANGE CUSTOM INTERFACE")
        }
        Button(
            onClick = onDebouncedClick {
                personDataState.value = personDataState.value.copy(
                    prim = Random.nextInt()
                )
            }
        ) {
            Text("Shuffle WITH CHANGE PRIM")
        }
    }

}

@Composable
fun StabilityChildDemo(
    personData: () -> Person,
    modifier: Modifier = Modifier
) {
    val person = personData()
    person.action()
    Text(
        text = "${person.name} is ${person.age} years old. listData=${person.list}",
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xEFE,
    showSystemUi = false,
    apiLevel = 33,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait",
    fontScale = 1.0f,
)
@Composable
private fun ComposeCompilerDemoPreview() {
    KoinApplication(
        application = { modules(mainModule) }
    ) {
        ComposePlaygroundTheme {
            StabilityDemo()
        }
    }
}