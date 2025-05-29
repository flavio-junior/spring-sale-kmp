package br.com.conding.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.conding.tv.navigation.Navigation
import br.com.conding.tv.theme.Theme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Theme {
                        Navigation(navController = rememberNavController())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {

}
