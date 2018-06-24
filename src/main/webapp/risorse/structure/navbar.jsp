<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar fixed-top navbar-dark navbar-expand-md">
    <a class="navbar-brand" href="/biblioteca/">
      <img src="/biblioteca/img/common/logo3.png" width="40" height="40" alt="Logo">
    </a>
    <button class="navbar-toggler navbar-dark menu" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse">
      <ul class="navbar-nav mr-auto mt-2 mt-md-0">
        <li class="nav-item active">
          <a class="nav-link" href="/biblioteca/">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/biblioteca/catalogo">Catalogo</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/biblioteca/areapersonale">Area riservata</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/biblioteca/faq">FAQ</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/biblioteca/dovesiamo">Dove siamo</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/biblioteca/contatti">Contatti</a>
        </li>
      </ul>
    </div>
    <c:if test="${param.nomeUtente!=null && param.nomeUtente!='' }">
    <div class="welcome">
    <c:if test="${param.admin}">
      <a href="/biblioteca/admin" class="btn btn-link">Admin</a>
    </c:if>
    <c:if test="${!param.admin}">
      Ciao <span>${param.nomeUtente}</span>
    </c:if>
      <button type="button" name="button" class="btn btn-sm btn-warning" onclick="location.href='/biblioteca/logout';">Esci</button>
    </div>
    </c:if>
    
  </nav>
  <div class="aside-hidden-screen d-block d-md-none">
    <div class="aside-dark-all"></div>
    <div class="aside-screen center col-sm-7">
      <h1 style="padding:20px">Menu</h1>
      <button type="button" class="close close-menu" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <div class="list-elem list-elem-home"><a href="/biblioteca/">Home</a></div>
      <div class="list-elem"><a href="/biblioteca/catalogo">Catalogo</a></div>
      <div class="list-elem"><a href="/biblioteca/login">Area riservata</a></div>
      <div class="list-elem"><a href="/biblioteca/faq">FAQ</a></div>
      <div class="list-elem"><a href="/biblioteca/dovesiamo">Dove siamo</a></div>
      <div class="list-elem"><a href="/biblioteca/contatti">Contatti</a></div>
    </div>
  </div>
  <c:if test="${param.msgVisual}">
  <div class="alert alert-${param.msgTipo} alert-dismissible col-sm-4 offset-sm-4" role="alert">
   ${param.msgTesto}
   <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    <span aria-hidden="true">&times;</span>
  </button>
 </div>
 </c:if>