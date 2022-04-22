package com.kapirti.zikirmatik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.kapirti.zikirmatik.ui.theme.ZikirmatikTheme
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class MainActivity : ComponentActivity() {
    val catsViewModel by viewModels<CatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZikirmatikTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    Home(
                        catsViewModel.cats,
                        onAddCat = {catsViewModel.addCat(it)},
                        onRemove = {catsViewModel.removeCat(it)}
                    )
                }
            }
        }
    }
}

class CatsViewModel : ViewModel(){
    var cats by mutableStateOf(CatsRepo.getCats())

    fun addCat(cat: Cat){
        cats = cats + listOf(cat)
    }
    fun removeCat(cat: Cat){
        cats = cats.toMutableList().also{
            it.remove(cat)
        }

    }
}

@Composable
fun Home(
    cats : List<Cat>,
    onAddCat : (Cat) -> Unit,
    onRemove : (Cat) -> Unit
){
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddCat(generateRandomCats())
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ){ innerPadding->
        LazyColumn(contentPadding = innerPadding) {
            item {
                Header(text = "Cats available for adoption")
            }
            items(cats) { cat->
                CatListItem(cat=cat, onRemove= onRemove)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CatListItem(cat:Cat, onRemove : (Cat) -> Unit){
    ListItem(
        modifier = Modifier.clickable { onRemove(cat) }.padding(8.dp),
        icon = {
            Image(
                painter = painterResource(id = cat.catImage),
                contentDescription = null,
                modifier = Modifier.clip(MaterialTheme.shapes.small)
            )
        },
        text = {
            Text(
                text = cat.name,
                style = MaterialTheme.typography.h6
            )
        },
        secondaryText = {
            Text(
                text = cat.gender,
                style = MaterialTheme.typography.body2,
                color = Color.Black.copy(.5f)
            )
        }
    )
}

@Composable
fun Header(
    text : String,
    modifier: Modifier = Modifier
){
    Surface(
        color = MaterialTheme.colors.onSurface.copy(.1f),
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle2,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}



data class Cat(
    val name: String,
    val gender: String = "Male",
    @DrawableRes val catImage: Int,
)

private val catName = listOf(
    "Daffy",
    "Tweety",
    "Charlie",
    "Yogi",
    "Felix",
    "Shaggy",
    "Scooby",
    "Wanda",
    "Gary",
    "Oscar",
    "Elmo",
    "Bert",
    "Ernie",
    "Kermi",
    "River",
    "Rocky",
    "Summit",
    "Clover",
    "Aspen",
    "Birch",
    "Brook",
    "Cedar"
)

private val image = listOf(
    R.drawable.ic_launcher_background,
    R.drawable.ic_launcher_foreground,
    R.drawable.ic_launcher_background,
    R.drawable.ic_launcher_foreground,
    R.drawable.ic_launcher_background,
    R.drawable.ic_launcher_foreground,
    R.drawable.ic_launcher_background,
)

private fun cats(): List<Cat> {
    val cats = mutableListOf<Cat>()
    for ((index, cat) in catName.withIndex()) {
        if (index % 2 == 0) {
            cats.add(Cat(cat, "female", image.random()))
        } else {
            cats.add(Cat(cat, catImage = image.random()))
        }
    }
    return cats
}

object CatsRepo {
    fun getCats(): List<Cat> = cats()
}

fun generateRandomCats(): Cat {
    return cats().random()
}