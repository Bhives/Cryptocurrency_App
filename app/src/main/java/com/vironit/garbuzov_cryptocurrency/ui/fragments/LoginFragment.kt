package com.vironit.garbuzov_cryptocurrency.ui.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.vironit.garbuzov_cryptocurrency.R
import com.vironit.garbuzov_cryptocurrency.databinding.FragmentLoginBinding
import com.vironit.garbuzov_cryptocurrency.ui.templates.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    val RC_SIGN_IN = 1
    var googleSignInClient: GoogleSignInClient? = null
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this.requireActivity(), gso)
        signInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(android.Manifest.permission.READ_CONTACTS),
            100
        )
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(ContentValues.TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    auth.currentUser
                    findNavController().navigate(R.id.action_loginFragment_to_cryptoCurrenciesFragment)
                } else {
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

}