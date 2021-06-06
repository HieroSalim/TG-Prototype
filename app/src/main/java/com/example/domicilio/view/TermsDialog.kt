import com.example.domicilio.view.ActivityLogin

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class TermsDialog: AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Politica de privacidade e uso de dados")
            .setMessage("Levamos muito a sério a sua privacidade. É política do Mobile Health respeitar a sua privacidade em relação a qualquer informação sua que possamos coletar no sistema Mobile Health.\n" +
                    "\n" +
                    "Solicitamos informações pessoais, pois se tratando de uma aplicação envolvendo saúde, precisamos delas para lhe fornecer nossos serviços. Tudo feito por meios justos e legais, com o seu conhecimento e consentimento. Também informamos por que estamos coletando e como será usado. \n" +
                    "\n" +
                    "Apenas retemos as informações coletadas pelo tempo necessário para fornecer o serviço solicitado. Quando armazenamos dados, protegemos dentro de meios aceitáveis para evitar perdas e roubos, bem como acesso, divulgação, cópia, uso ou modificação não autorizados.\n" +
                    "\n" +
                    "Não compartilhamos informações de identificação pessoal publicamente ou com terceiros, exceto quando exigido por lei.\n" +
                    "\n" +
                    "Nossa aplicação depende de terceiros, nós somos intermediadores dos serviços deles para com vocês. Fazemos de maneira a concordar com as normas exigidas por lei para o atendimento médico a domicílio, pedindo informações e documentos necessários para a área atuante do profissional em questão. Os mesmos, se enquadrando nas políticas da aplicação. Esteja ciente de que não temos controle sobre as práticas desses profissionais e não podemos aceitar responsabilidade por suas respectivas políticas de privacidade. Oferecemos total suporte referente a qualquer desvio padrão na abordagem, conforme Lei Federal nº 12.527/2011.\n" +
                    "\n" +
                    "Você é livre para recusar a nossa solicitação de informações pessoais, entendendo que talvez não possamos fornecer alguns dos serviços desejados.\n" +
                    "\n" +
                    "O uso continuado de nosso sistema será considerado como aceitação de nossas práticas em torno de privacidade e informações pessoais. Se você tiver alguma dúvida sobre como lidamos com dados do usuário e informações pessoais, entre em contato conosco.")
            .setPositiveButton("Concordo", DialogInterface.OnClickListener {dialog, id ->

            })

            .setNegativeButton("Discordo", DialogInterface.OnClickListener{dialog, id ->
                var intent : Intent = Intent(context, ActivityLogin::class.java)
                var activity = activity
                startActivity(intent)
                activity?.finish()
            } )

        return builder.create()
    }
}