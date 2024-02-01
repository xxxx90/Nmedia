package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import androidx.activity.viewModels
import androidx.core.view.isVisible
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.util.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }

//            override fun cancell() {
//                viewModel.cancell()
//            }


        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                binding.groupOnEdit.isVisible=true
                binding.edit.setText(post.content)
                binding.edit.requestFocus()

            }
        }
        binding.closeEdit.setOnClickListener {
            binding.edit.setText("")
            binding.edit.clearFocus()
            AndroidUtils.hideKeyboard(it)
            binding.groupOnEdit.isVisible=false
            viewModel.cancell()

        }
        binding.save.setOnClickListener {

            val text = binding.edit.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }
            viewModel.changeContentAndSave(text)
            binding.edit.setText("")
            binding.edit.clearFocus()
            AndroidUtils.hideKeyboard(it)
            binding.groupOnEdit.isVisible=false

        }


    }
}



