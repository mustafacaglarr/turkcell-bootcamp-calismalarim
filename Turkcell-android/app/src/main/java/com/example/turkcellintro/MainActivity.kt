package com.example.turkcellintro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.turkcellintro.ui.theme.TurkcellIntroTheme
import com.example.turkcellintro.viewmodel.ToDoListViewModel

// Burada ekran tanımlarını yap.
sealed class Screen(val route: String) {
    data object Register: Screen("register")
    data object Homepage: Screen("homepage")
}

// Telefon çevirildiği an => Yeniden başlatılır.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold {
                    paddingValues ->  MyNavigatableApp(Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun MyNavigatableApp(modifier: Modifier) {
    val navController = rememberNavController()
    // Magic String
    Column() {
        NavHost(navController=navController, startDestination = Screen.Homepage.route){
            composable(Screen.Register.route) { RegisterScreen(modifier, navController) }
            composable(Screen.Homepage.route) { Homepage(modifier) }
        }
    }
}

@Composable
fun Homepage(modifier: Modifier)
{
    // View -> ViewModel'i çağırır -> Dependency Injection
    val viewModel: ToDoListViewModel = viewModel();

    Column(modifier= modifier.fillMaxSize()) {
        Text("To Do List")
        AddToDo(onAdd = {text -> viewModel.addToDo(text)})
        ToDoList(viewModel.toDoList, onDelete = {i->viewModel.deleteToDo(i)})
    }

}
@Composable
fun AddToDo(onAdd: (String) -> Unit) {
    var text = remember { mutableStateOf("abc") }

    Column() {
        Text("Todo List")
        TextField(
            value=text.value, // Statik olduğu sürece ekranda güncellenmez.
            onValueChange = { newValue -> text.value = newValue }
        )
        Button(
            onClick = {
                onAdd(text.value)
                text.value = ""
            }
        ) {
            Text("Tıkla")
        }
    }
}
// State aynı
@Composable
fun ToDoList(toDoList: List<String>, onDelete: (Int) -> Unit) {

    LazyColumn(modifier = Modifier.fillMaxSize())
    {
        itemsIndexed(toDoList) {
                index,todo ->
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(todo)
                IconButton(onClick = {
                    onDelete(index)
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Sil")
                }
            }
        }
    }
}