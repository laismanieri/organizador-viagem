package br.edu.utfpr.organizadordeviagem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class MainActivityFirts extends AppCompatActivity {

    private EditText
            editTextCheckIn,
            editTextCheckOut,
            editTextItemName,
            editTextAccomodationName,
            editTextAccomodationAddress,
            editTextOneWay,
            editTextReturn,
            editTextOriginTransport,
            editTextDestinationTransport;
    private Spinner spinnerTransport;
    private LinearLayout checkBoxContainer;
    private RadioButton radioButtonOneWay, radioButtonRoundTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);

        spinnerTransport = findViewById(R.id.spinnerTransport);

        editTextCheckIn = findViewById(R.id.editTextCheckIn);
        editTextCheckOut = findViewById(R.id.editTextCheckOut);

        editTextAccomodationName = findViewById(R.id.editTextAccomodationName);
        editTextAccomodationAddress = findViewById(R.id.editTextAccomodationAddress);

        checkBoxContainer = findViewById(R.id.checkBoxContainer);
        editTextItemName = findViewById(R.id.editTextItemName);

        radioButtonOneWay = findViewById(R.id.radioButtonOneWay);
        radioButtonRoundTrip = findViewById(R.id.radioButtonRoundTrip);

        editTextOriginTransport = findViewById(R.id.editTextOriginTransport);
        editTextDestinationTransport = findViewById(R.id.editTextDestinationTransport);
        editTextOneWay = findViewById(R.id.editTextOneWay);
        editTextReturn = findViewById(R.id.editTextReturn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transport_modes, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTransport.setAdapter(adapter);

        editTextCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextCheckIn);
            }
        });

        editTextCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextCheckOut);
            }
        });

        editTextOneWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextOneWay);
            }
        });

        editTextReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editTextReturn);
            }
        });

        // Configuração do Spinner
        setupSpinner();

    }

    public void addItem(View view) {
        String itemName = editTextItemName.getText().toString();
        if (!itemName.isEmpty()) {
            // Cria um layout horizontal para conter o CheckBox e o botão de remover
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Cria o CheckBox
            CheckBox newCheckBox = new CheckBox(this);
            newCheckBox.setText(itemName);
            newCheckBox.setTextColor(getResources().getColor(R.color.colorTextInput));


            // Define parâmetros de layout para o CheckBox
            LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                    0, // largura
                    LinearLayout.LayoutParams.WRAP_CONTENT, // altura
                    1.0f // peso para ocupar espaço disponível
            );
            newCheckBox.setLayoutParams(checkBoxParams);
            // Cria o botão de remover
            TextView removeButton = new TextView(this);
            removeButton.setBackgroundResource(R.drawable.rounded_remove_list);
            removeButton.setText("X");
            removeButton.setTextColor(getResources().getColor(android.R.color.white));
            removeButton.setTextSize(10);
            removeButton.setGravity(View.TEXT_ALIGNMENT_CENTER | View.TEXT_ALIGNMENT_CENTER);
            removeButton.setPadding(16, 8, 0, 8);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBoxContainer.removeView(itemLayout);
                    Toast.makeText(MainActivityFirts.this, "Item removido", Toast.LENGTH_SHORT).show();
                }
            });


            // Define o layout do botão para ser pequeno e redondo com margem à esquerda
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    50, // largura
                    50  // altura
            );
            buttonParams.setMargins(10, 0, 0, 0); // margem à esquerda para espaço entre CheckBox e botão
            removeButton.setLayoutParams(buttonParams);
            // Adiciona o CheckBox e o botão de remover ao layout horizontal
            itemLayout.addView(newCheckBox);
            itemLayout.addView(removeButton);

            // Adiciona o layout horizontal ao container de CheckBoxes
            checkBoxContainer.addView(itemLayout);

            // Limpa o campo de entrada de texto
            editTextItemName.setText("");
        } else {
            Toast.makeText(this, "Por favor, insira um nome para o item", Toast.LENGTH_SHORT).show();
        }
    }


    public void clearFieldsList(View view) {

        // Desmarca os CheckBoxes
        for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
            View v = checkBoxContainer.getChildAt(i);
            if (v instanceof LinearLayout) {
                LinearLayout itemLayout = (LinearLayout) v;
                for (int j = 0; j < itemLayout.getChildCount(); j++) {
                    View child = itemLayout.getChildAt(j);
                    if (child instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) child;
                        checkBox.setChecked(false);
                    }
                }
            }
        }

        Toast.makeText(this, "Campos limpos", Toast.LENGTH_SHORT).show();
    }

    public void clearFieldsTransport(View view) {
        // Limpar EditTexts


        editTextOriginTransport.setText("");
        editTextDestinationTransport.setText("");
        editTextOneWay.setText("");
        editTextReturn.setText("");

        // Desmarcar RadioButtons
        radioButtonOneWay.setChecked(false);
        radioButtonRoundTrip.setChecked(false);

        // Desmarcar CheckBoxes
        LinearLayout checkBoxContainer = findViewById(R.id.checkBoxContainer);
        for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
            View childView = checkBoxContainer.getChildAt(i);
            if (childView instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) childView;
                checkBox.setChecked(false);
            }
        }

        // Mostrar Toast
        Toast.makeText(this, "Campos limpos", Toast.LENGTH_SHORT).show();
    }

    // Método para configurar o Spinner de transporte
    private void setupSpinner() {
        Spinner spinnerTransport = findViewById(R.id.spinnerTransport);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transport_modes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTransport.setAdapter(adapter);
    }

    private void showDatePickerDialog(final EditText editText) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    public void clearFieldsAccomodation(View view) {
        // Limpar os EditText
        editTextCheckIn.setText("");
        editTextCheckOut.setText("");
        editTextItemName.setText("");
        editTextAccomodationName.setText("");
        editTextAccomodationAddress.setText("");;

        // Mostrar um Toast indicando a ação realizada
        Toast.makeText(this, "Campos limpos", Toast.LENGTH_SHORT).show();
    }

    public void clearFields(View view) {
        // Limpar os EditText
        editTextCheckIn.setText("");
        editTextCheckOut.setText("");
        editTextItemName.setText("");
        editTextAccomodationName.setText("");
        editTextAccomodationAddress.setText("");;

        // Desmarcar os RadioButtons
        radioButtonOneWay.setChecked(false);
        radioButtonRoundTrip.setChecked(false);

        // Desmarcar os CheckBoxes
        for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
            View v = checkBoxContainer.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) v;
                checkBox.setChecked(false);
            }
        }

        // Mostrar um Toast indicando a ação realizada
        Toast.makeText(this, "Campos limpos", Toast.LENGTH_SHORT).show();
    }

    // Método para salvar os detalhes da acomodação
    public void saveAccomodationDetails(View view) {
        // Verificar se algum EditText está vazio
        if (editTextAccomodationName.getText().toString().isEmpty()) {
            showToastErrorAndFocus("Por favor, insira o nome da acomodação", editTextAccomodationName);
            return;
        }

        if (editTextAccomodationAddress.getText().toString().isEmpty()) {
            showToastErrorAndFocus("Por favor, insira o endereço", editTextAccomodationAddress);
            return;
        }

        if (editTextCheckIn.getText().toString().isEmpty()) {
            showToastErrorAndFocus("Por favor, insira a data do checking", editTextCheckIn);
            return;
        }

        if (editTextCheckOut.getText().toString().isEmpty()) {
            showToastErrorAndFocus("Por favor, insira a data do checkout", editTextCheckOut);
            return;
        }

        Toast.makeText(this, "Detalhes da acomodação salvos", Toast.LENGTH_SHORT).show();
    }

    public void saveTransportDetails(View view) {

        // Verificar se algum EditText está vazio
        if (editTextOriginTransport.getText().toString().isEmpty()) {
            showToastErrorAndFocus("Por favor, insira a origem", editTextOriginTransport);
            return;
        }

        if (editTextDestinationTransport.getText().toString().isEmpty()) {
            showToastErrorAndFocus("Por favor, insira o destino", editTextDestinationTransport);
            return;
        }

        if (radioButtonOneWay.isChecked() || radioButtonRoundTrip.isChecked()) {
            if (radioButtonOneWay.isChecked() && editTextOneWay.getText().toString().isEmpty()) {
                showToastErrorAndFocus("Por favor, insira a data da ida", editTextOneWay);
                return;
            }

            if (radioButtonRoundTrip.isChecked() && (editTextOneWay.getText().toString().isEmpty()
                    || editTextReturn.getText().toString().isEmpty())) {
                showToastErrorAndFocus("Por favor, insira as datas de ida e volta", editTextOneWay);
                return;
            }
        } else {
            showToastError("Por favor, selecione o tipo de viagem");
            return;
        }

        // Obter valores selecionados do Spinner
        String selectedTransport = spinnerTransport.getSelectedItem().toString();

        // Salvar os detalhes (substitua com a lógica apropriada para salvar os dados)

        // Mostrar Toast de sucesso
        Toast.makeText(this, "Detalhes de transporte salvos", Toast.LENGTH_SHORT).show();
    }

    private void showToastError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showToastErrorAndFocus(String message, EditText editText) {
        showToastError(message);
        editText.requestFocus();
    }


}