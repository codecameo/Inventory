package wit.bytes.inventory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import wit.bytes.inventory.R;
import wit.bytes.inventory.interfaces.ILoginProvider;
import wit.bytes.inventory.models.api_models.LoginResponseModel;
import wit.bytes.inventory.providers.ProviderFactory;
import wit.bytes.inventory.receivers.result_receivers.LoginResultReceiver;
import wit.bytes.inventory.utils.Constants;
import wit.bytes.inventory.utils.PreferenceUtil;
import wit.bytes.inventory.wrappers.LoginAdapter;

/**
 * Created by Md. Sifat-Ul Haque on 2/20/2017.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginResultReceiver.Receiver {

    private EditText mEtUserName, mEtPassword;
    private Button mBtnLogin;
    private Handler mHandler;
    private LoginResultReceiver mLoginResultReceiver;
    private ProviderFactory mProviderFactory;
    private ILoginProvider mILoginProvider;
    private PreferenceUtil mPreferenceUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initVariables();
        initListeners();
        checkPreviousLogin();
    }

    private void checkPreviousLogin() {
        int user_id = mPreferenceUtil.getInt(Constants.KEY_USER_ID,-1);
        if (user_id != -1){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }

    private void initVariables() {
        mHandler = new Handler();
        mLoginResultReceiver = new LoginResultReceiver(mHandler);
        mProviderFactory = ProviderFactory.getProviderInstance();
        mILoginProvider = mProviderFactory.getProductProvider(this,mLoginResultReceiver);
        mPreferenceUtil = PreferenceUtil.getInstance(this);
    }

    private void initListeners() {
        mBtnLogin.setOnClickListener(this);
        mLoginResultReceiver.setReceiver(this);
    }

    private void initView() {
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtUserName = (EditText) findViewById(R.id.et_username);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void onClick(View v) {

        String username = mEtUserName.getText().toString();
        if (TextUtils.isEmpty(username)){
            mEtUserName.setError("This field is required");
            mEtPassword.requestFocus();
            return;
        }

        String password = mEtPassword.getText().toString();
        if (TextUtils.isEmpty(password)){
            mEtPassword.setError("This field is required");
            mEtPassword.requestFocus();
            return;
        }
        mBtnLogin.setEnabled(false);
        mILoginProvider.login(username, password);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        mBtnLogin.setEnabled(true);
        if (resultCode == Constants.API_RESPONSE_SUCCESSFUL && resultData != null){
            LoginResponseModel loginResponseModel = (LoginResponseModel) resultData.getSerializable(LoginAdapter.KEY_LOGIN_RESPONSE);
            mPreferenceUtil.setString(Constants.KEY_USER_NAME, loginResponseModel.getData().getName());
            mPreferenceUtil.setInt(Constants.KEY_USER_ID, loginResponseModel.getData().getUser_id());
            mPreferenceUtil.setString(Constants.KEY_ACCESS_TOKEN, loginResponseModel.getData().getAccess_token());
        }
    }
}
