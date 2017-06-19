var app = require('http').createServer(handler) 
 , io = require('socket.io').listen(app)
 , fs = require('fs');
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
  socket.on('sendalbum', function(msg){
      io.sockets.emit('albumshow', {data : msg.data, familyid : msg.familyid});
  });
  socket.on('deletealbum', function(msg){
      io.sockets.emit('albumdelete', {data : msg.data, familyid : msg.familyid});
  });
});