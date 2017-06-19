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
  socket.nickname = nick.nickname;
  socket.broadcast.emit('announcement', {user: socket.nickname, action: 'connected'});
  	    /*if (nicknames[nickname]) {
      fn(true);
    } else {
      fn(false);
      nicknames[nickname] = socket.nickname = nickname;
      socket.broadcast.emit('announcement', {user: nickname, action: 'connected'});
      //io.sockets.emit('nicknames', nicknames);
    }*/
  /*socket.on('addme',function(username) {
  socket.username = username;
  socket.emit('chat', username, ' welcome!'); 
  socket.broadcast.emit('chat', username, ' connected....');*/
  //io.sockets.emit('nicknames', nicknames);
 });
 socket.on('sendchat', function(msg) { 
  io.sockets.emit('user message', { user: socket.nickname, message: msg.message });
  //io.sockets.emit('chat', socket.username, data);
 });
 socket.on('disconnect', function() {
  socket.broadcast.emit('announcement', {user: socket.nickname, action: 'leaved'});
  //socket.broadcast.emit('stateChange', socket.username);
 });
 socket.on('immediatelysend', function(account, invitename, familyname, familyid, inviteid){
   socket.broadcast.emit('immediatelyask', account, invitename, familyname, familyid, inviteid);
 });
 socket.on('sendagree', function(name){
   socket.broadcast.emit('newmember', name);
 });
 socket.on('sendboard', function(filedata){
  socket.broadcast.emit('boardupload', socket.username, filedata);
 });
 socket.on('deleteboard', function(fileid){
  socket.broadcast.emit('boarddelete', fileid);
 });
 socket.on('modifystatement', function(fileid, filetitle){
  socket.broadcast.emit('statementmodify', fileid, filetitle);
 });
 socket.on('modifydegree', function(fileid, filedegree){
  socket.broadcast.emit('degreemodify', fileid, filedegree);
 });
 socket.on('modifyposition', function(position, ex_position){
  socket.broadcast.emit('positionmodify', position, ex_position);
 });
});