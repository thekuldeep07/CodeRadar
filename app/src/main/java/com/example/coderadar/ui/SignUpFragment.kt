package com.example.coderadar.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.coderadar.mvvm.LoginViewModel

import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentSignUpBinding

class   SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))
            .get(LoginViewModel::class.java)
        mainFunction()
        return binding.root
    }


    fun mainFunction() {
        val emailPattern: Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

        binding.registerConstraintLayout.setOnClickListener {
            binding.confirmPassError.error = null
            binding.registerEmailError.error = null
            binding.passError.error = null
        }

        binding.SignUp.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.backConstraintlayout.alpha = 0.3f
            val name = binding.editText.text.toString()
            val email = binding.editText2.text.toString().trim()
            val pass = binding.editText3.text.toString()
            val confirmPass = binding.editText4.text.toString()
            if (pass != confirmPass) {
                binding.progressBar.visibility = View.GONE
                binding.backConstraintlayout.alpha = 1f
                binding.confirmPassError.error = "Password does not match."
            } else if (pass.length < 8) {
                binding.progressBar.visibility = View.GONE
                binding.backConstraintlayout.alpha = 1f
                binding.confirmPassError.error = "Password should be 8 character long."
            }else if(!email.matches(emailPattern)) {
                binding.progressBar.visibility = View.GONE
                binding.backConstraintlayout.alpha = 1f
                binding.registerEmailError.error = "Please Enter valid email."
            } else {
                signUP(name, email, pass)
            }
        }

        binding.registertologinbutton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    fun signUP(name: String, email: String, pass: String){
        viewModel.signUp(name, email, pass)
        viewModel.firebaseUserData.observe(this.requireActivity(), {
            if (it != null){
                binding.progressBar.visibility = View.GONE
                binding.backConstraintlayout.alpha = 1f
                binding.editText.setText("")
                binding.editText2.setText("")
                binding.editText3.setText("")
                binding.editText4.setText("")
                val customDialog = LayoutInflater.from(this.requireContext()).inflate(R.layout.custom_dialog_design, null)
                val alert = AlertDialog.Builder(this.requireActivity())
                alert.setView(customDialog)
                val dialog = alert.create()
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val ok = customDialog.findViewById<Button>(R.id.custom_dialog_ok_button)
                dialog.show()
                ok.setOnClickListener {
                    dialog.dismiss()
                }
            }
        })
    }
}