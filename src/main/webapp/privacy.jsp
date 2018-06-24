<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Privacy policy | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>

  </head>
  <body>
    <jsp:include page="./risorse/structure/navbar.jsp">
     <jsp:param value="${utente.nome}" name="nomeUtente"/>
     <jsp:param value="${utente.admin}" name="admin"/>
    </jsp:include>
    <div class="container-fluid first-section">
      <div class="layer-band center">
        <h2 class="display-4">Privacy policy</h2>
      </div>

      <div class="layer-section">
       <h3>{{subtitle}}</h3>
       
        
       
      </div>
    </div>


   <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
