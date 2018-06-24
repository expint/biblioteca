<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Biblioteca online</title>
<%@ include file="./risorse/structure/script.html" %>
<link rel="stylesheet" href="css/home.css">
</head>
<body>
 <jsp:include page="./risorse/structure/navbar.jsp">
  <jsp:param value="${utente.nome}" name="nomeUtente"/>
  <jsp:param value="${utente.admin}" name="admin"/>
  <jsp:param value="${msg.testo}" name="msgTesto"/>  
  <jsp:param value="${msg.tipo}" name="msgTipo"/>  
  <jsp:param value="${msg.visualizzato}" name="msgVisual"/>  
 </jsp:include>
  <div class="container-fluid center first-section">
    <div class="layer-band center">
      <h2 class="display-4">Biblioteca online</h2>
    </div>
    <div class="layer-section">
      <div class="description col-md-12 mx-auto">
        <div class="row justify-content-around">
          <a href="./catalogo" class="a-home">
            <img src="img/common/book-img3.png" alt="" width="200px" height="200px">
            <span class="img-home-content">
              <h2>Catalogo</h2>
            </span>
          </a>
          <a href="./areapersonale" class="a-home">
            <img src="img/common/user-img3.png" alt="" width="200px" height="200px">
            <span class="img-home-content">
              <h2>Accedi</h2>
            </span>
          </a>
          <a href="#avvisi" class="a-home">
            <img src="img/common/news-img3.png" alt="" width="200px" height="200px">
            <span class="img-home-content">
              <h2>Avvisi</h2>
            </span>
          </a>
        </div>
      </div>

    </div>
  </div>

      <div class="container-fluid center second-section">
        <div class="layer-band">
          <a id="avvisi"><h2 class="display-4">Avvisi</h2></a>
        </div><br>
          <div class="card text-center mx-auto col-md-4">
            <div class="card-header">
              <h5>Nuovo sito</h5>
            </div>
            <div class="card-body">
              <p class="card-text">
              Benvenuti nel nuovo sito della biblioteca.<br>
              Nella sezione del catalogo scoprite le ultime novit&agrave;!
              Utilizzate la sezione "Contatti" per farci sapere le vostre impressioni e i vostri consigli!
              </p>
              <p>Buona navigazione e buone letture!</p>
            </div>
            <div class="card-footer text-muted">
              2 giorni fa
            </div>
          </div>
      </div> <!-- fine second section -->

     <%@ include file="./risorse/structure/footer.html" %>
</body>
</html>
