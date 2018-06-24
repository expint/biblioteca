<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Dove siamo | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/dovesiamo.css">
  </head>
  <body>
			<jsp:include page="./risorse/structure/navbar.jsp">
			  <jsp:param value="${utente.nome}" name="nomeUtente"/>
			  <jsp:param value="${utente.admin}" name="admin"/>
			 </jsp:include>
    <div class="container-fluid first-section">
      <div class="layer-band center">
        <h2 class="display-4">Dove siamo</h2>
      </div>

      <div class="layer-section" id="libri">
        <h3>Mappa</h3>
        <div class="row justify-content-around">
          <div class="mappa">
            <iframe width="550" height="400" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.openstreetmap.org/export/embed.html?bbox=7.644081115722656%2C45.05775891491667%2C7.709312438964845%2C45.09267127368945&amp;layer=mapnik" style="border: 1px solid black"></iframe><br><small><a href="https://www.openstreetmap.org/#map=14/45.0752/7.6767">Visualizza mappa ingrandita</a></small>
          </div>
          <div class="dettagli col-lg-4">
            <p class="lead">La nostra sede legale, amministrativa e operativa si trova a Torino, in Italia.</p>
            <h4>Come raggiungerci</h4>
            <p class="lead">Al momento la biblioteca è attiva solamente online, quindi se volete contattarci utilizzate il modulo alla pagina <a href="/biblioteca/contatti">Contatti</a></p>
          </div>
        </div>



      </div>
    </div>


    <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
