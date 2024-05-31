package com.example.assignment.composables.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment.R
import com.example.assignment.ui.theme.ClockColor
import com.example.assignment.ui.theme.DarkGrayColor
import com.example.assignment.ui.theme.StarColor
import com.example.assignment.ui.theme.SubTitleColor
import com.example.assignment.ui.theme.TitleColor
import com.example.network.models.Restaurant

/**
 * Restaurant list item
 * @param restaurant Restaurant to display
 * @param onClick Callback when the list item is clicked
 */
@Composable
fun RestaurantListItem(
    restaurant: Restaurant,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .clickable { onClick() }
    ) {
        Box {
            RestaurantImage(
                restaurant = restaurant,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = restaurant.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = TitleColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = StarColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${restaurant.rating}",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Text(
                    text = restaurant.getFilterText(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SubTitleColor
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = null,
                        tint = ClockColor,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = restaurant.getFormattedDeliveryTime(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkGrayColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RestaurantListItemPreview() {
    RestaurantListItem(
        restaurant = Restaurant(
            id = "1",
            name = "Restaurant name",
            rating = 5f,
            imageUrl = "image url",
            filterIds = emptyList(),
            deliveryTimeMinutes = 30
        ),
        onClick = {}
    )
}
