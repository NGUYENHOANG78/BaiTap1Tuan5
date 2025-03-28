package com.example.myapplication.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("YOUR_WEB_CLIENT_ID") // 🔥 Thay bằng Firebase Client ID!
            .requestEmail()
            .build()
    )

    var isSigningIn by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            // ✅ Đăng nhập thành công → Chuyển sang màn hình chính
                            navController.navigate("taskList")
                        } else {
                            // ❌ Đăng nhập thất bại → Hiển thị thông báo lỗi
                            Toast.makeText(context, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show()
                        }
                        isSigningIn = false
                    }
            } catch (e: ApiException) {
                Toast.makeText(context, "Lỗi: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                isSigningIn = false
            }
        } else {
            isSigningIn = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "UTH Logo",
            modifier = Modifier.size(120.dp)
        )

        Text("SmartTasks", fontSize = 28.sp, modifier = Modifier.padding(top = 16.dp))
        Text("A simple and efficient to-do app", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        if (isSigningIn) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    isSigningIn = true
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                },
                modifier = Modifier
                    .width(250.dp) // ✅ Giảm chiều dài nút
                    .height(60.dp), // ✅ Cố định chiều cao hợp lý
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gg),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp) // ✅ Giữ icon nhỏ gọn
                )
                Spacer(Modifier.width(7.dp)) // ✅ Giảm khoảng cách giữa icon và chữ
                Text("Sign in with Google", fontSize = 16.sp) // ✅ Rút gọn chữ trên nút
            }
        }
    }
}
