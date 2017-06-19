var app = require('http').createServer(handler) 
 , io = require('socket.io').listen(app)
 , nicknames = {}
 , fs = require('fs');
app.listen(3000);

function handler (req, res) { 
 fs.readFile(__dirname + '/testtable.aspx', function (err, data) {
     if (err) { 
                res.writeHead(500);
         return res.end('Error loading chat.html'); 
            }
     res.writeHead(200);
     res.end(data); 
        });
}
io.sockets.on('connection', function (socket) {
  socket.on('nickname', function (nick) {
      socket.username = nick.nickname;
	  socket.familyid = nick.familyid;
      socket.broadcast.emit('announcement', {user: socket.username, action: 'connected', familyid: socket.familyid});
  });
  socket.on('disconnect', function(msg) {
      socket.broadcast.emit('announcement', {user: socket.username, action: 'leaved', familyid: socket.familyid});
      //socket.broadcast.emit('stateChange', socket.username);
  });
  socket.on('sendchat', function(msg) { 
      socket.broadcast.emit('user message', { user: msg.user, message: msg.message, headimage: msg.headimage, familyid: socket.familyid , time : msg.time });
      //io.sockets.emit('chat', socket.username, data);
  });
  socket.on('immediatelysend', function(msg){
      socket.broadcast.emit('immediatelyask', { account : msg.account, invitename : msg.invitename, familyname : msg.familyname, 
	                                            familyid : msg.familyid, inviteid : msg.inviteid });
  });
  socket.on('leavefamily', function(msg){
      socket.broadcast.emit('familyleave', { username : socket.username, familyid : msg.familyid });
  });
  socket.on('sendagree', function(msg){
      socket.broadcast.emit('newmember', { newname : msg.newname, headImage: msg.headImage, familyid: msg.familyid });
  });
  socket.on('changefamilyname', function(msg){
      socket.broadcast.emit('familynamechange', { username: msg.username, newfamilyname: msg.newfamilyname, familyid: msg.familyid });
  });
  socket.on('changeusername', function(msg){
      socket.broadcast.emit('usernamechange', { oldname: msg.oldname, newname: msg.newname, familyid: msg.familyid });
  });
  socket.on('changeheadimage', function(msg){
      socket.broadcast.emit('headimagechange', { username: msg.username, headimage: msg.headimage, familyid: msg.familyid });
  });
  socket.on('sendboard', function(msg){
      socket.broadcast.emit('boardupload', msg);
      //socket.broadcast.emit('boardupload', { username : socket.username, filedata : msg.filedata, familyid : msg.familyid });
  });
  socket.on('senddraw', function(msg){
      io.sockets.emit('drawsend', { username : socket.username, filedata : msg.filedata, position : msg.position, familyid : msg.familyid });  
  });
  socket.on('deleteboard', function(msg){
      socket.broadcast.emit('boarddelete', { fileid : msg.fileid, familyid : msg.familyid });
  });
  socket.on('modifystatement', function(msg){
      socket.broadcast.emit('statementmodify', { fileid : msg.fileid, title : msg.title, familyid : msg.familyid });
  });
  socket.on('modifydegree', function(msg){
      socket.broadcast.emit('degreemodify', { fileid : msg.fileid, rotate : msg.rotate, familyid : msg.familyid });
  });
  socket.on('modifyposition', function(msg){
      socket.broadcast.emit('positionmodify', { last : msg.last, ex : msg.ex, familyid : msg.familyid });
  });
  socket.on('sendpasta', function(msg){
      io.sockets.emit('showpasta', { pastatype : msg.pastatype, familyid : msg.familyid, filedata : msg.filedata });
  });
  socket.on('sendmessage', function(msg){
      socket.broadcast.emit('messagesend', msg);
  });
  socket.on('deletemessage', function(msg){
      socket.broadcast.emit('messagedelete', { contentid: msg.contentid, familyid: msg.familyid });
  });
});