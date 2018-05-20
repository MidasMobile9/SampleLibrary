package com.test.samplelibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.test.samplelibrary.data.Name;
import com.test.samplelibrary.network.NetworkDefineConstant;
import com.test.samplelibrary.network.OkHttpAPICall;
import com.test.samplelibrary.network.OkHttpInitSingletonManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHTTP3AndGSONActivity extends AppCompatActivity {
    static final String TAG = "OkHttp3AndGSONActivity";

    @BindView(R.id.buttonGET) Button buttonGET;
    @BindView(R.id.buttonPOST) Button buttonPOST;
    @BindView(R.id.buttonUPDATE) Button buttonUPDATE;
    @BindView(R.id.buttonDELETE) Button buttonDELETE;
    @BindView(R.id.editTextNo) EditText editTextNo;
    @BindView(R.id.editTextName) EditText editTextName;
    @BindView(R.id.textViewResult) TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http3_and_gson);
        // ButterKnife 세팅
        ButterKnife.bind(this);
    }

    @OnClick({R.id.buttonGET, R.id.buttonPOST, R.id.buttonUPDATE, R.id.buttonDELETE})
    public void onButtonClick(View view) {
        switch ( view.getId() ) {
            case R.id.buttonGET:
                new NamesGetTask().execute();

                break;
            case R.id.buttonPOST:
                String newName = editTextName.getText().toString().trim();

                if ( newName == null || newName.length() < 1 ) {
                    Toast.makeText(OkHTTP3AndGSONActivity.this, "새 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new NewNamePostTask().execute(newName);

                break;
            case R.id.buttonUPDATE:
                if ( editTextNo.getText().toString().trim() == null || editTextNo.getText().toString().trim().length() < 1 ) {
                    Toast.makeText(OkHTTP3AndGSONActivity.this, "변경할 이름의 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ( editTextName.getText().toString().trim() == null || editTextName.getText().toString().trim().length() < 1 ) {
                    Toast.makeText(OkHTTP3AndGSONActivity.this, "변경할 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int updateNo = Integer.parseInt(editTextNo.getText().toString().trim());
                String updateName = editTextName.getText().toString().trim();

                new NameUpdateTask().execute(updateNo, updateName);

                break;
            case R.id.buttonDELETE:
                if ( editTextNo.getText().toString().trim() == null || editTextNo.getText().toString().trim().length() < 1 ) {
                    Toast.makeText(OkHTTP3AndGSONActivity.this, "삭제할 이름의 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int deleteNo = Integer.parseInt(editTextNo.getText().toString().trim());

                new NameDeleteTask().execute(deleteNo);

                break;
            default:
                break;
        }

        editTextNo.setText("");
        editTextName.setText("");

        // 키보드 내리기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextNo.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(editTextName.getWindowToken(), 0);
    }

    public class NamesGetTask extends AsyncTask<Void, Void, List<Name>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Name> doInBackground(Void... voids) {
            List<Name> nameList = getNames();

            return nameList;
        }

        @Override
        protected void onPostExecute(List<Name> nameList) {
            super.onPostExecute(nameList);

            if ( nameList == null ) {
                // 통신 실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Toast.makeText(OkHTTP3AndGSONActivity.this, message, Toast.LENGTH_SHORT).show();
            } else {
                // 통신 성공

                String result = "";

                for ( int i = 0; i < nameList.size(); i++ ) {
                    result = result + nameList.get(i).getNo() + " : " + nameList.get(i).getName() + "\n";
                }

                textViewResult.setText(result);
            }
        }
    }

    public class NewNamePostTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String newName = strings[0];
            boolean isAdded = addNewName(newName);

            return isAdded;
        }

        @Override
        protected void onPostExecute(Boolean isAdded) {
            super.onPostExecute(isAdded);

            if ( isAdded ) {
                // 통신 성공
                String message = "새 이름을 추가하였습니다.";
                Toast.makeText(OkHTTP3AndGSONActivity.this, message, Toast.LENGTH_SHORT).show();
            } else {
                // 통신 실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Toast.makeText(OkHTTP3AndGSONActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class NameUpdateTask extends AsyncTask<Object, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Object... objects) {
            int updateNo = 0;
            String updateName = "";

            if ( objects[0] instanceof Integer ) {
                updateNo = (Integer)objects[0];
            }

            if ( objects[1] instanceof String ) {
                updateName = (String)objects[1];
            }

            boolean isUpdated = updateName(updateNo, updateName);

            return isUpdated;
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            super.onPostExecute(isUpdated);

            if ( isUpdated ) {
                // 통신 성공
                String message = "새로운 이름으로 변경하였습니다.";
                Toast.makeText(OkHTTP3AndGSONActivity.this, message, Toast.LENGTH_SHORT).show();
            } else {
                // 통신 실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Toast.makeText(OkHTTP3AndGSONActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class NameDeleteTask extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            int deleteNo = 0;

            if ( integers[0] instanceof Integer ) {
                deleteNo = (Integer)integers[0];
            }

            boolean idDeleted = deleteName(deleteNo);

            return idDeleted;
        }

        @Override
        protected void onPostExecute(Boolean idDeleted) {
            super.onPostExecute(idDeleted);

            if ( idDeleted ) {
                // 통신 성공
                String message = "이름을 삭제하였습니다.";
                Toast.makeText(OkHTTP3AndGSONActivity.this, message, Toast.LENGTH_SHORT).show();
            } else {
                // 통신 실패
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Toast.makeText(OkHTTP3AndGSONActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*********************************************************************************************************************************************/
    /*********************************************************************************************************************************************/
    /*********************************************************************************************************************************************/
    /*********************************************************************************************************************************************/

    public List<Name> getNames() {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        // GSON : 서버로부터 넘어온 JSON 데이터를 자동으로 파싱(Name 클래스 코드 확인할 것!)
        Gson gson = new Gson();

        List<Name> nameList = new ArrayList<>();
        String message = null;

        try {
            response = OkHttpAPICall.GET(client, NetworkDefineConstant.SERVER_URL_GET_DATA);

            if ( response == null ) {
                Log.e(TAG, "Response of getNames() is null.");

                return null;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                // Name List 저장
                if ( jsonFromServer.has("nameList") ) {
                    JSONArray nameArray = jsonFromServer.getJSONArray("nameList");

                    for ( int i = 0; i < nameArray.length(); i++ ) {
                        Name name = gson.fromJson(nameArray.getJSONObject(i).toString(), Name.class);
                        nameList.add(name);
                    }
                }

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }

        return nameList;
    }

    public boolean addNewName(String newName) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        boolean isAdded = false;
        String message = null;

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("newName", newName)
                .build();

        try {
            response = OkHttpAPICall.POST(client, NetworkDefineConstant.SERVER_URL_POST_DATA, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of addNewName() is null.");

                return false;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                isAdded = jsonFromServer.getBoolean("result");

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }

        return isAdded;
    }

    public boolean updateName(int updateNo, String updateName) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        boolean isUpdated = false;
        String message = null;

        // 수정할 데이터의 정보를 담은 RequestBody 생성
        RequestBody requestBody = new FormBody.Builder()
                .add("updateNo", String.valueOf(updateNo))
                .add("updateName", updateName)
                .build();

        try {
            response = OkHttpAPICall.PUT(client, NetworkDefineConstant.SERVER_URL_UPDATE_DATA, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of updateName() is null.");

                return false;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                isUpdated = jsonFromServer.getBoolean("result");

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }

        return isUpdated;
    }

    public boolean deleteName(int deleteNo) {
        OkHttpClient client = OkHttpInitSingletonManager.getOkHttpClient();
        Response response = null;

        boolean isDeleted = false;
        String message = null;

        // 삭제할 데이터의 정보를 담은 RequestBody 생성
        RequestBody requestBody = new FormBody.Builder()
                .add("deleteNo", String.valueOf(deleteNo))
                .build();

        try {
            response = OkHttpAPICall.PUT(client, NetworkDefineConstant.SERVER_URL_DELETE_DATA, requestBody);

            if ( response == null ) {
                Log.e(TAG, "Response of deleteName() is null.");

                return false;
            } else {
                JSONObject jsonFromServer = new JSONObject(response.body().string());

                isDeleted = jsonFromServer.getBoolean("result");

                if ( jsonFromServer.has("message") ) {
                    message = jsonFromServer.getString("message");
                    Log.e(TAG, message);
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( response != null ) {
                response.close();
            }
        }

        return isDeleted;
    }
}
