<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Utenti da approvare | Biblioteca online</title>
    <%@ include file="risorse/structure/script.html" %>
    <link rel="stylesheet" href="/biblioteca/css/admin.css">
    <script>
      $(document).ready(function(){
        $(".role").click(function(){
          if ($(this).parents("tr").find(".approva").val()=="0") {
            $(this).parents("tr").find(".approva").val("1");
            $(this).text("Admin");
            $(this).removeClass("btn-secondary").addClass("btn-primary");
          }
          else {
            $(this).parents("tr").find(".approva").val("0");
            $(this).text("User");
            $(this).removeClass("btn-primary").addClass("btn-secondary");
          }
        });

      });
    </script>
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
        <h2 class="display-4">Utenti da approvare</h2>
      </div>

      <div class="layer-section center">
        <div class="table-responsive">
        <c:if test="${empty utenti}">
         <h3>Nessun utente da approvare</h3><br>
         <a href="/biblioteca/admin">Torna alla dashboard</a>
        </c:if>
        <c:if test="${!empty utenti}">
                
          <table class="table">
            <thead>
              <tr>
                <th scope="col">id</th>
                <th scope="col">Nome</th>
                <th scope="col">Cognome</th>
                <th scope="col">Mail</th>
                <th scope="col">Role</th>
                <th scope="col">Modifica</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="utenti" items="${utenti}">
              <tr>
                <td>${utenti.utente.id}</td>
                <td>${utenti.utente.nome}</td>
                <td>${utenti.utente.cognome}</td>
                <td>${utenti.utente.mail}</td>
                <td><button type="button" class="btn btn-sm btn-secondary role">User</button></td>
                <td>
                <div class="row justify-content-around">
                  <form action="/biblioteca/admin/utenti/approva/${utenti.utente.id}" method="post">
                    <button type="submit" class="btn btn-sm btn-success approva"  name="approvaBtn" value="0">Approva</button>
                  </form>
                  <form action="/biblioteca/admin/utenti/rimuovi/${utenti.utente.id}" method="post">
                    <button type="submit" class="btn btn-sm btn-danger rimuovi" name="rimuovi">Rimuovi</button>
                  </form>
                </div>
                </td>
            </tr>
            </c:forEach>
            
            
          </tbody>
        </table>
        </c:if>
      </div>



    </div>
  </div>

  <%@ include file="./risorse/structure/footer.html" %>
  </body>
</html>
