var app = require('http').createServer(handler) 
 , io = require('socket.io').listen(app)
 , fs = require('fs');
 var XMLHttpRequest = require("w3c-xmlhttprequest").XMLHttpRequest;
app.listen(3030);

function handler (req, res) { 
 fs.readFile(__dirname + '/album.aspx', function (err, data) {
     if (err) { 
                res.writeHead(500);
         return res.end('Error loading chat.html'); 
            }
     res.writeHead(200);
     res.end(data); 
        });
}
io.sockets.on('connection', function (socket) {
/*
  socket.on('sendalbum', function(msg){
      io.sockets.emit('albumshow', {data : msg.data, familyid : msg.familyid});
  });*/
  socket.on('sendalbum', function(msg){
      io.sockets.emit('albumshow', {data : msg.data, image: msg.coverimage, familyid : msg.familyid});
  });
  socket.on('initalbum', function(msg){
        
        var oReq = new XMLHttpRequest();
		oReq.open('POST', 'http://203.64.183.224:8080/ashx/album/Handler3.ashx',true );
		oReq.setRequestHeader("CONTENT-TYPE", "application/x-www-form-urlencoded");
        oReq.onreadystatechange = function () {
          if (oReq.readyState == 4) {
              if (oReq.status == 200 || oReq.status == 304) {
			      var json = JSON.parse(oReq.responseText);
				  console.log("%s",oReq.responseText);
				  io.sockets.socket(socket.id).emit('initalbum_success', {albumid : json.albumid, albumname : json.albumname, cover:  json.cover, total:  json.total});
              } else {
                  // error
              }
          }
      }
      oReq.send(msg.familyid);
  });
  socket.on('deletealbum', function(msg){
      io.sockets.emit('albumdelete', {data : msg.filename, familyid : msg.familyid});
  });
  socket.on('modifyalbumname', function(msg){
      io.sockets.emit('albumnamemodify', {fileid : msg.fileid, albumname : msg.albumname, familyid : msg.familyid});
  });
});

/*  
  socket.on('deletealbum', function(msg){
      io.sockets.emit('albumdelete', {data : msg.filename, familyid : msg.familyid});
  });
  socket.on('modifyalbumname', function(msg){
      io.sockets.emit('albumnamemodify', {fileid : msg.fileid, albumname : msg.albumname, familyid : msg.familyid});
  });
});*/