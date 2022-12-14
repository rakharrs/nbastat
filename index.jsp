<%@ page import="nba.com.entit.Match" %>
<%@ page import="nba.com.obj.Joueur" %>
<%@ page import="nba.com.stat.Possession" %>
<%@ page import="dbObject.MyConnexion" %>
<%@ page import="nba.com.stat.Detail" %>
<%@ page import="nba.com.stat.possessionTime" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.ArrayList" %>

<%
    //String idMatches = "MAT0038";
    //String idMatches = "MAT0010";
    String idMatches = Detail.getLastIdMatches();
    Match match = null;
    Possession pos = new Possession(idMatches);
    possessionTime[] pt = pos.getIntervalleStat();
    Connection con = MyConnexion.getConnection();
    /*ArrayList<Object> joueurs = MyConnexion.sqltoArray(con, new Joueur(), "select * from joueur where idjoueur = '"+pt[pt.length-1].getIdJoueur()+"'");
    Joueur j = (Joueur) joueurs.get(0);*/
    Joueur j = Joueur.getJoueur(con, pt[pt.length-1].getIdJoueur());
    con.close();

    try{
        match = new Match(idMatches);
    }catch (Exception e){ %>
        <%=e%>
    <% }%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/bootstrap.css">
    <title>Match</title>
</head>
<body>
<div class="container-fluid row my-5">
    <div class="text-center">
        <h2 class="text-primary">Temps de jeu: <%=pos.getTotalPlaytime()%> s</h2>
    </div>
    <div class="text-center">
        <h2 class="text-primary"> <%=j.getNomJoueur()%> : <%=pt[pt.length-1].getValue()%> s</h2>
    </div>
    <div class="col-md-6 text-center">
        <h1 class="text-primary"><%=match.getTeam1().getEquipe().getNomEquipe() %>: <%=match.getTeam1().getScore(idMatches) %></h1>
        <h2 class="text-primary"> <%=pos.getTeamPlaytime(match.getTeam1().getEquipe().getIdEquipe())+"s"%></h2>
        <h2 class="text-primary"> <%=((double)(Math.round(((double)pos.getPossessionPercent(match.getTeam1().getIdEquipe())*100))))/100+"%"%></h2>
    </div>
    <div class="col-md-6 text-center">
        <h1 class="text-primary"><%=match.getTeam2().getEquipe().getNomEquipe() %>: <%=match.getTeam2().getScore(idMatches) %></h1>
        <h2 class="text-primary"> <%=pos.getTeamPlaytime(match.getTeam2().getEquipe().getIdEquipe())+"s"%></h2>
        <h2 class="text-primary"> <%=((double)(Math.round(((double)pos.getPossessionPercent(match.getTeam2().getIdEquipe())*100))))/100+"%"%></h2>
    </div>
</div>
<div class="ms-5 container-fluid row">
        <div class="container col">
            <% for (int i = 0; i < match.getTeam1().getJoueurs().length; i++) {
                Joueur joueur = match.getTeam1().getJoueurs()[i];
            %>
                <h2><%=match.getTeam1().getJoueurs()[i].getNomJoueur() %> : </h2>
                <table class="table">
                    <tr>
                        <th>Action</th>
                        <th>Statistique</th>
                    </tr>
                    <tr>
                        <td>Tirs</td>
                        <td><%=joueur.getSuccess_Shoot(idMatches)%>/<%=joueur.getTotalShoot(idMatches) %></td>
                    </tr>
                    <tr>
                        <td>Passes D</td>
                        <td><%=joueur.getTotalPASSE_D(idMatches)%></td>
                    </tr>
                    <tr>
                        <td>Rebonds OFF</td>
                        <td><%=joueur.getTotalOff_Rebounds(idMatches)%></td>
                    </tr>
                    <tr>
                        <td>Rebonds DEF</td>
                        <td><%=joueur.getTotalDEF_Rebounds(idMatches)%></td>
                    </tr>
                    <tr>
                        <td>Points</td>
                        <td><%=joueur.getTotalPoints(idMatches)%></td>
                    </tr>
                    <tr>
                        <td>Temps de possession</td>
                        <td><%=pos.getPlayerPlaytime(joueur.getIdJoueur())%>s</td>
                    </tr>
                </table>
            <% } %>
        </div>
       <div class="container col">
           <% for (int i = 0; i < match.getTeam2().getJoueurs().length; i++) {
               Joueur joueur = match.getTeam2().getJoueurs()[i];
           %>
           <h2><%=match.getTeam2().getJoueurs()[i].getNomJoueur() %> : </h2>
           <table class="table">
               <tr>
                   <th>Action</th>
                   <th>Statistique</th>
               </tr>
               <tr>
                   <td>Tirs</td>
                   <td><%=joueur.getSuccess_Shoot(idMatches)%>/<%=joueur.getTotalShoot(idMatches) %></td>
               </tr>
               <tr>
                   <td>Passes D</td>
                   <td><%=joueur.getTotalPASSE_D(idMatches)%></td>
               </tr>
               <tr>
                   <td>Rebonds OFF</td>
                   <td><%=joueur.getTotalOff_Rebounds(idMatches)%></td>
               </tr>
               <tr>
                   <td>Rebonds DEF</td>
                   <td><%=joueur.getTotalDEF_Rebounds(idMatches)%></td>
               </tr>
               <tr>
                   <td>Points</td>
                   <td><%=joueur.getTotalPoints(idMatches)%></td>
               </tr>
               <tr>
                   <td>Temps de possession</td>
                   <td><%=pos.getPlayerPlaytime(joueur.getIdJoueur())%>s</td>
               </tr>
           </table>
           <% } %>
        </div>
</div>
</body>
<script src="./assets/js/bootstrap.min.js"></script>
<script src="./assets/js/jquery.min.js"></script>
</html>