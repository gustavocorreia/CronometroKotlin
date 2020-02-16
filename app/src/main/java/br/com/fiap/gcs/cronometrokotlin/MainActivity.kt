package br.com.fiap.gcs.cronometrokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Número de segundos exibidos no cronometro
    private var segundos = 0
    // O cronômetro está funcionando?
    private var executando: Boolean = false
    private var estavaExecutando: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null){
            segundos = savedInstanceState.getInt("segundos")
            executando = savedInstanceState.getBoolean("executando")
            estavaExecutando = savedInstanceState.getBoolean("estavaExecutando")
        }

        onClickIniciar()
        onClickParar()
        onClickReiniciar()

        executarCronometro()
    }

    override fun onPause() {
        super.onPause()
        estavaExecutando = executando
        executando = false
    }

    override fun onResume() {
        super.onResume()
        if(estavaExecutando)
            executando = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("segundos", segundos)
        outState.putBoolean("executando", executando)
        outState.putBoolean("estavaExecutando", estavaExecutando)
        super.onSaveInstanceState(outState)
    }

    fun onClickIniciar(){
        btIniciar.setOnClickListener {
            executando = true
        }
    }

    fun onClickParar(){
        btParar.setOnClickListener {
            executando = false
        }
    }

    fun onClickReiniciar(){
        btResetar.setOnClickListener {
            executando = false
            segundos = 0
        }
    }

    private fun executarCronometro(){
        val handler = Handler()

        handler.postDelayed({
            var horas = (segundos / 3600).toString().padStart(2, '0')
            var minutos = ((segundos % 3600) / 60).toString().padStart(2, '0')
            var segs = (segundos % 60).toString().padStart(2, '0')

            var tempo = "$horas:$minutos:$segs"
            tvTimer.text = tempo

            if (executando)
                segundos++

        }, 1000)
    }
}
