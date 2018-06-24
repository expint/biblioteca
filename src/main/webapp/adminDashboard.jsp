<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Admin Dashboard | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/libro.css">
    <link rel="stylesheet" href="/biblioteca/css/admin.css">
  </head>
  <body>
    <jsp:include page="./risorse/structure/adminNavbar.jsp">
     <jsp:param value="${utente.nome}" name="nomeUtente"/>
     <jsp:param value="${msg.testo}" name="msgTesto"/>  
     <jsp:param value="${msg.tipo}" name="msgTipo"/>  
     <jsp:param value="${msg.visualizzato}" name="msgVisual"/>
    </jsp:include>
    <div class="container-fluid first-section center">
      <div class="layer-band center">
        <h2 class="display-4">Dashboard</h2>
      </div>

      <div class="layer-section center">
        <div class="card-deck col-lg-10 offset-lg-1 mx-auto">
          <div class="card border-primary">
            <div class="card-body">
              <h5 class="card-title">Libri in catalogo</h5>
              <a href="/biblioteca/admin/catalogo" class="numero">${libri}</a>
            </div>
          </div>
          <div class="card border-primary">
            <div class="card-body">
              <h5 class="card-title">Utenti registrati</h5>
              <a href="/biblioteca/admin/utenti" class="numero">${utenti}</a>
            </div>
          </div>
          <div class="card border-primary">
            <div class="card-body">
              <h5 class="card-title">Prenotazioni attive</h5>
              <a href="/biblioteca/admin/prenotazioni/attive" class="numero">${prenAttive}</a>
            </div>
          </div>
        </div>
        <div class="card-deck col-lg-10 offset-lg-1 mx-auto">
          <div class="card border-primary">
            <div class="card-body">
              <h5 class="card-title">Contatti ricevuti</h5>
              <a href="/biblioteca/admin/contatti" class="numero">${contatti}</a>
            </div>
          </div>
          <div class="card border-primary">
            <div class="card-body">
              <h5 class="card-title">Utenti da approvare</h5>
              <a href="/biblioteca/admin/utenti/approva" class="numero">${daApprovare}</a>
            </div>
          </div>
          <div class="card border-primary">
            <div class="card-body">
              <h5 class="card-title">Prenotazioni totali</h5>
              <a href="/biblioteca/admin/prenotazioni" class="numero">${prenotazioni}</a>
            </div>
          </div>
        </div>



      </div>
    </div>


  <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
