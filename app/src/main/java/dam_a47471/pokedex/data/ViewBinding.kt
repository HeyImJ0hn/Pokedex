package dam_a47471.pokedex.data

import android.graphics.Bitmap
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import javax.sql.DataSource

object ViewBinding {
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: AppCompatImageView, resource: Int) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, resource))
    }

    /**/
    @JvmStatic
    @BindingAdapter("app:cardBackgroundColorType")
    fun setCardBackgroundColor(cardView: CardView, resource: Int) {
        cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, resource))
    }

    /**/
    @JvmStatic
    @BindingAdapter("paletteImage", "paletteCard")
    fun bindLoadImagePalette(
        view: AppCompatImageView, url: String, paletteCard:
        MaterialCardView
    ) {
        Glide.with(view.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("TAG", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    p1: Any?,
                    p2: Target<Bitmap>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    p4: Boolean
                ): Boolean {
                    Log.d("TAG", "OnResourceReady")
                    if (resource != null) {
                        val p: Palette = Palette.from(resource).generate()
                        val rgb = p.lightMutedSwatch?.rgb
                        if (rgb != null) {
                            paletteCard.setCardBackgroundColor(rgb)
                        }
                    }
                    return false
                }
            }).into(view)
    }

}