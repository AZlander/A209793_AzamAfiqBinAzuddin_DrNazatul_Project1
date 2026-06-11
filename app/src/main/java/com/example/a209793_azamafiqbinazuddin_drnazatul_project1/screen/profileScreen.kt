package com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.R
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data.UserProfile
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.ui.theme.GroveTheme

@Composable
fun ProfileScreen(viewModel: GroveViewModel) {
    val userProfile by viewModel.userProfile.collectAsState()
    ProfileScreenContent(
        userProfile = userProfile,
        onUpdateProfile = { name, bio -> viewModel.updateProfile(name, bio) }
    )
}

@Composable
fun ProfileScreenContent(
    userProfile: UserProfile,
    onUpdateProfile: (String, String) -> Unit
) {
    var editName by remember { mutableStateOf(userProfile.username) }
    var editBio by remember { mutableStateOf(userProfile.bio) }

    // Sync local edit fields whenever the ViewModel profile changes
    // (e.g. when name is updated from HomeScreen)
    LaunchedEffect(userProfile) {
        editName = userProfile.username
        editBio = userProfile.bio
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile Avatar",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userProfile.username,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = userProfile.bio,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = editName,
                    onValueChange = { editName = it },
                    label = { Text(stringResource(R.string.username)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = editBio,
                    onValueChange = { editBio = it },
                    label = { Text(stringResource(R.string.bio)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onUpdateProfile(editName, editBio) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.save_changes))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    GroveTheme {
        ProfileScreenContent(
            userProfile = UserProfile(
                username = "Grove Explorer",
                bio = "Planting new ideas."
            ),
            onUpdateProfile = { _, _ -> }
        )
    }
}
