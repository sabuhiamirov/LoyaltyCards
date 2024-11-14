package com.cardfy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.navigation.Route
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrgenerator.barcodepainter.BarcodeFormat
import qrgenerator.barcodepainter.EmptyPainter
import qrgenerator.barcodepainter.rememberBarcodePainter
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner

@Preview
@Composable
fun HomeScreen(
    navController: NavController
) {

    //  val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var isQrScanCode by remember { mutableStateOf(false) }

    val barCodePainter = rememberBarcodePainter(content = "2000 0000 0003 5386",
        format = BarcodeFormat.Code128,
        brush = SolidColor(Color.Black),
        onError = { throwable ->
            /*   scope.launch {
                   snackbarHostState.showSnackbar("Error occurred: ${throwable.message}")
               }*/
            // Return a fallback painter, for example, an empty painter
            EmptyPainter()
        })

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 10.dp),
                title = {
                    Text(
                        text = "Loyalty Cards",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        isQrScanCode = !isQrScanCode
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = ""
                        )
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            )
        },
        snackbarHost ={
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(vertical = 10.dp, horizontal = 20.dp),
            ) {
                items(2) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp),
                        shape = RoundedCornerShape(6.dp),
                        backgroundColor = Color.Green,
                    ) {

                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {

                            Text(
                                modifier = Modifier.padding(
                                    bottom = 20.dp
                                ).fillMaxWidth(),
                                text = "Araz Kart",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.caption,
                                fontSize = 16.sp
                            )

                            Image(
                                painter = barCodePainter,
                                contentDescription = null,
                                modifier = Modifier.background(Color.White),
                            )


                            Text(
                                modifier = Modifier.padding(
                                    top = 10.dp, bottom = 10.dp
                                ).fillMaxWidth(),
                                text = "2000 0000 0003 5386",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,
                                fontSize = 16.sp
                            )
                        }
                    }

                }
            }

        }

        if (isQrScanCode) {
            scanBarcode(
                onCompletion = {
                    isQrScanCode = false
                    navController.navigate("${Route.SUCCESS_SCANNED_SCREEN}/${it}")
                },
                onFailure = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            )
        }

    }
}

@Composable
fun scanBarcode(onCompletion: (String) -> Unit, onFailure: (String) -> Unit) {
    QrScanner(
        flashlightOn = false,
        onCompletion = {
            onCompletion.invoke(it)
        },
        overlayColor = Color.LightGray,
        overlayShape = OverlayShape.Square,
        imagePickerHandler = {

        },
        onFailure = {
            onFailure.invoke(it)
        },
        overlayBorderColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        cameraLens = CameraLens.Back,
        customOverlay = {
            drawRect(
                color = Color.Black.copy(alpha = 0.5f), topLeft = Offset.Zero, size = size
            )
        },
        openImagePicker = true
    )
}