package com.mostafatamer.postlibrary.presentation.shared_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mostafatamer.postlibrary.domain.model.Post

@Composable
fun PostCardContent(post: Post) {
    Row {
        Text(
            text = post.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = post.id.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = post.body.replace("\n", " "), textAlign = TextAlign.Justify)
}