<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>FAQ | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/areapersonale.css">
    <script>
    $(document).ready(function(){
      $(".accordion .btn").click(function(){
        $(this).siblings().slideToggle();

        if ($(this).val()=="0") {
          $(this).css("border-radius", "2px 2px 0 0");
          $(this).val("1");
          $(this).find("span").html("&#9650;");
        }
        else {
          $(this).css("border-radius", "2px");
          $(this).val("0");
          $(this).find("span").html("&#9660;");
        }
      });
    });
    </script>
  </head>
  <body>
    <jsp:include page="./risorse/structure/navbar.jsp">
     <jsp:param value="${utente.nome}" name="nomeUtente"/>
     <jsp:param value="${utente.admin}" name="admin"/>
    </jsp:include>
    <div class="container-fluid first-section">
      <div class="layer-band center">
        <h2 class="display-4">FAQ</h2>
      </div>

      <div class="layer-section">
        <div class="card accordion col-lg-5 mx-auto border-primary">
          <button class="btn btn-block btn-primary btnFaq" value="0">Come si fa a prenotare un libro?<span class="badge accordionBadgeFaq">&#9660;</span></button>
          <div class="card-body">
            <p>Per prenotare un libro bisogna prima registrarsi (Sezione "Area riservata").</p>
            <p>Dopo la convalida dell'amministratore si potranno prenotare i libri.</p>
          </div>
        </div>
        <div class="card accordion col-lg-5 mx-auto border-primary">
          <button class="btn btn-block btn-primary btnFaq" value="0">Quanti libri posso prenotare?<span class="badge accordionBadgeFaq">&#9660;</span></button>
          <div class="card-body">
            <p>Puoi prenotare massimo 3 libri per volta.</p>
          </div>
        </div>
        <div class="card accordion col-lg-5 mx-auto border-primary">
          <button class="btn btn-block btn-primary btnFaq" value="0">Come si fa a restituire una copia?<span class="badge accordionBadgeFaq">&#9660;</span></button>
          <div class="card-body">
            <p>Nell'area riservata si possono vedere le prenotazioni attive.</p>
            <p>Basterà cliccare il tasto "Restituisci".</p>
          </div>
        </div>
        <div class="card accordion col-lg-5 mx-auto border-primary">
          <button class="btn btn-block btn-primary btnFaq" value="0">Ho riscontrato un problema, come ve lo comunico?<span class="badge accordionBadgeFaq">&#9660;</span></button>
          <div class="card-body">
            <p>Nella sezione contatti potrai mandare un messaggio all'amministratore del sistema.</p>
          </div>
        </div>

      </div>
    </div>


   <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
