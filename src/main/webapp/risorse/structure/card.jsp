<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Card personale | Biblioteca online</title>
  </head>
  <body>
    <div class="container-fluid">
      <div class="cardPersonale" style="width:400px; background-color:#c4ab9a; border-radius:5px; margin:auto">
        <div class="barranav" style="display:block; height:30px; background-color:#3e0608;border-radius:5px 5px 0 0;"></div>
        <div class="titolo" style="display:block; height:40px; line-height: 40px; background-color:#f6f4f2; margin-top:10px;text-align:center">
          <h2 style="margin:0;font-family:Georgia">Biblioteca online</h2>
        </div>
        <div class="cardPersonaleBody" style="width: 90%; border-radius: 5px; display:block; height:200px; line-height: 50px; background-color:#f6f4f2; margin: 10px auto;
        text-align:center">
          <span style="font-size:24px;font-family:Georgia">Card personale</span>
          <div class="dati"  style="margin: -10px 50px 10px 50px; font-family:Georgia">
            <div class="nome" style="clear: both; margin:0">
              <div style="float:left; height: 20px">
                <em><strong>Nome:</strong></em>
              </div>
              <div style="float:right">
                ${param.nomeUtente}
              </div>
            </div>
            <div class="cognome" style="clear: both; margin:0">
              <div style="float:left">
                <em><strong>Cognome:</strong></em>
              </div>
              <div style="float:right">
                ${param.cognomeUtente}
              </div>
            </div>
            <div class="codice" style="clear: both; margin:0">
              <div style="float:left">
                <em><strong>Codice:</strong></em>
              </div>
              <div style="float:right">
                ${param.idUtente}
              </div>
            </div>

          </div>
        </div>
        <div class="barranav" style="display:block; height:30px; background-color:#3e0608;border-radius:0px 0px 5px 5px;"></div>
      </div>
    </div>
  </body>
</html>
