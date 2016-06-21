package br.com.cimobile.livrosfirebase;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.cimobile.livrosfirebase.databinding.ActivityMainBinding;
import br.com.cimobile.livrosfirebase.databinding.ItemLivroBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference livroRef;

    private ActivityMainBinding mBinding;
    private FirebaseRecyclerAdapter<Livro,LivroHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mBinding =  DataBindingUtil.setContentView(this,R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    finish();

                    startActivity(new Intent(MainActivity.this,SignInActivity.class));
                }
                // ...
            }
        };

        initFireBase();
        initUI();
    }

    private void initUI() {

        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FirebaseRecyclerAdapter<Livro, LivroHolder>(Livro.class, R.layout.item_livro, LivroHolder.class, livroRef) {
            @Override
            public void populateViewHolder(LivroHolder livroViewHolder, Livro livro, int position) {
                livroViewHolder.setLivro(livro);

            }
        };
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private void initFireBase() {

        database = FirebaseDatabase.getInstance();
        livroRef = database.getReference("livros").child(mAuth.getCurrentUser().getUid());


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_sign_up){
            FirebaseAuth.getInstance().signOut();
    }
        return super.onOptionsItemSelected(item);
    }


    public void clickGoogleSignUp(View view) {
        FirebaseAuth.getInstance().signOut();
    }

    public void escreverClick(View view) {

        Livro  livro = new Livro("Dominando o Android","Nelson Glauber",null);

        livroRef.push().setValue(livro);



   /*     // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference myRef2 = database.getReference("android");
        myRef.setValue("Hello, World!");
        myRef2.setValue("Show");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                TextView txt = (TextView) findViewById(R.id.textView);
                txt.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }

    public void cliclNovoLivro(View view) {

        startActivity(new Intent(this,CadastroActivity.class));
    }


    public static class LivroHolder extends RecyclerView.ViewHolder {
        ItemLivroBinding mBinding;

        public LivroHolder(View view) {
            super(view);
           mBinding  = DataBindingUtil.bind(view);
        }

        public void setLivro(Livro livro) {
                    mBinding.setLivro(livro);
        }

    }
}
