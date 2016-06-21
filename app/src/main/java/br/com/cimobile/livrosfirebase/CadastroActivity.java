package br.com.cimobile.livrosfirebase;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.ui.ActivityHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.cimobile.livrosfirebase.databinding.ActivityCadastroBinding;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference livroRef;

    private ActivityCadastroBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_cadastro);

        mBinding.setLivro(new Livro());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        livroRef = database.getReference("livros").child(auth.getCurrentUser().getUid());
    }

    public void clickSave(View view) {
        livroRef.push().setValue(mBinding.getLivro());
        finish();

    }
}
