package com.example.a209793_azamafiqbinazuddin_drnazatul_project1.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.ui.theme.GroveTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.GroveScreen
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.R
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data.StudyBoard
import com.example.a209793_azamafiqbinazuddin_drnazatul_project1.data.UserProfile

@Composable
fun HomeScreen(viewModel: GroveViewModel, navController: NavController) {
    val userProfile by viewModel.userProfile.collectAsState()
    val boards by viewModel.boards.collectAsState()

    // Helper for bottom-nav-style navigation (Library & Search are bottom nav destinations)
    val navigateToBottomNavDest: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    HomeScreenContent(
        userProfile = userProfile,
        boards = boards,
        onUpdateProfile = { name, bio -> viewModel.updateProfile(name, bio) },
        onNavigateToAddBoard = { navController.navigate("AddBoard") },
        onNavigateToLibrary = { navigateToBottomNavDest(GroveScreen.Library.name) },
        onNavigateToSearch = { navigateToBottomNavDest(GroveScreen.Search.name) },
        onNavigateToBoardDetail = { boardId -> navController.navigate("BoardDetail/$boardId") }
    )
}

@Composable
fun HomeScreenContent(
    userProfile: UserProfile,
    boards: List<StudyBoard>,
    onUpdateProfile: (String, String) -> Unit,
    onNavigateToAddBoard: () -> Unit,
    onNavigateToLibrary: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToBoardDetail: (Int) -> Unit
) {
    //mutablestateof
    var inputText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()



    // Unique subjects count from the boards list
    val subjectsCount = boards.map { it.subject }.distinct().size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        // 1. Header (kept from original)
        GroveHeader(userName = userProfile.username)

        // 2. Name input row (kept from original)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text(stringResource(R.string.enter_your_name)) },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (inputText.isNotBlank()) {
                    onUpdateProfile(inputText, userProfile.bio)
                    inputText = ""
                }
            }) {
                Text(stringResource(R.string.update))
            }
        }

        // 3. Stats Card
        StatsCard(
            boardsCount = boards.size,
            subjectsCount = subjectsCount
        )

        // 4. Quick Actions
        QuickActionsRow(
            onAddBoard = onNavigateToAddBoard,
            onLibrary = onNavigateToLibrary,
            onSearch = onNavigateToSearch
        )

        // 5. Continue Studying (only if boards exist)
        if (boards.isNotEmpty()) {
            ContinueStudyingSection(
                boards = boards.takeLast(3).reversed(),
                onBoardClick = onNavigateToBoardDetail
            )
        }

        // 6. SDG 4 Mission Card (now expandable with image)
        SdgMissionCard()

        // 7. Daily Study Tip (now expandable with image)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun GroveHeader(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Grove",
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            val greeting = if (userName.isNotBlank() && userName != "Grove Explorer") {
                stringResource(id = R.string.Question) + ", $userName?"
            } else {
                stringResource(id = R.string.Question)
            }
            Text(greeting, fontSize = 13.sp)
        }
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun StatsCard(boardsCount: Int, subjectsCount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = stringResource(R.string.boards),
                value = boardsCount.toString(),
                emoji = "📚"
            )
            VerticalDivider(
                modifier = Modifier.height(80.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f)
            )
            StatItem(
                label = stringResource(R.string.subjects),
                value = subjectsCount.toString(),
                emoji = "📖"
            )
        }
    }
}

@Composable
fun StatItem(label: String, value: String, emoji: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun QuickActionsRow(
    onAddBoard: () -> Unit,
    onLibrary: () -> Unit,
    onSearch: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.quick_actions),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickActionCard(
                icon = Icons.Filled.Add,
                label = stringResource(R.string.new_board),
                onClick = onAddBoard,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.AutoMirrored.Filled.List,
                label = stringResource(R.string.library),
                onClick = onLibrary,
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                icon = Icons.Filled.Search,
                label = stringResource(R.string.search),
                onClick = onSearch,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun QuickActionCard(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ContinueStudyingSection(
    boards: List<StudyBoard>,
    onBoardClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = stringResource(R.string.continue_studying),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(boards) { board ->
                RecentBoardCard(board = board, onClick = { onBoardClick(board.id) })
            }
        }
    }
}

@Composable
fun RecentBoardCard(board: StudyBoard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = board.title,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = board.subject,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                maxLines = 1
            )
            if (board.goal.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "🎯 ${board.goal}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun SdgMissionCard() {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column {
            // Hero image at the top
            Image(
                painter = painterResource(id = R.drawable.sdg4),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentScale = ContentScale.Crop
            )

            // Text content below the image
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🌱", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(R.string.sdg),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.sdgheader2),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                    )

                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.sdgdesc1) +
                                    stringResource(R.string.sdgdesc2) +
                                    stringResource(R.string.sdgdesc3) +
                                    stringResource(R.string.sdgdesc4) +
                                    stringResource(R.string.sdgdesc5) +
                                    stringResource(R.string.sdgdesc6),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DailyTipCard(tip: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            // Hero image at the top
            Image(
                painter = painterResource(id = R.drawable.tips),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                contentScale = ContentScale.Crop
            )

            // Text content below the image
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💡", fontSize = 22.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Daily Study Tip",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tip,
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (expanded) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.tipdesc1) +
                                    stringResource(R.string.tipdesc2) +
                                    stringResource(R.string.tipsdesc3) +
                                    stringResource(R.string.tipdesc4) +
                                    stringResource(R.string.tipdesc5) +
                                    stringResource(R.string.tipsdesc6),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleUserProfile = UserProfile(username = "Azam Afiq", bio = "Planting new ideas.")
    val sampleBoards = listOf(
        StudyBoard(1, "Kotlin Basics", "Programming", "Learn syntax"),
        StudyBoard(2, "Compose UI", "Design", "Master layouts"),
        StudyBoard(3, "Android Architecture", "Development", "Understand MVVM")
    )

    GroveTheme {
        HomeScreenContent(
            userProfile = sampleUserProfile,
            boards = sampleBoards,
            onUpdateProfile = { _, _ -> },
            onNavigateToAddBoard = {},
            onNavigateToLibrary = {},
            onNavigateToSearch = {},
            onNavigateToBoardDetail = {}
        )
    }
}