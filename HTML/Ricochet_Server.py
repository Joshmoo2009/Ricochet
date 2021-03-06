from tornado import websocket, web, ioloop, httpserver
import tornado, json

userIP = dict()

def CheckJoin(self):
	msg=dict()
	if len(userIP.keys()) < 2:
		userIP[self.request.remote_ip] = self
		msg["type"]="Joined"; #set your type here
		if len(userIP.keys())  == 1:
			msg["data"]="WAITING_FOR_PLAYERS";
			msg["ID"] = 0
			msg=json.dumps(msg)
			self.write_message(msg)
		else:
			msg["data"]="ALL_PLAYERS_READY";
			msg["ID"] = 1
			#sendIDToAllButPlayer(self, msg)
			self.write_message(msg)
			msg["data"]="STARTING_GAME";
			sendToAll(msg)
	else:
		msg["type"]="Join UnsuccessFul - Session Full"
		self.write_message(msg)

def sendToAll(msg):
	for key in userIP:
		userIP[key].write_message(msg)		
		
def sendIDToAllButPlayer(self, msg):
	for key in userIP:
		if key != self.request.remote_ip:
			msg["type"] = "Message"
			msg["IP"] = self.request.remote_ip
			msg=json.dumps(msg)
			userIP[key].write_message(msg)
			print("Message Sent To: " + self.request.remote_ip)
	
def sendBulletToOtherPlayer(self, R):
	for key in userIP:
		if key != self.request.remote_ip:
			#Creates the dictionary
			msg=dict()
			
			#sendIDToAllButPlayer(self, msg)
			
			#Populates the message
			msg["type"] = "Spawn Bullet"
			msg["Pos"] = {"X":R["Pos"]["X"], "Y":R["Pos"]["Y"]}
			msg["Dir"] = R["Dir"]
			msg["ID"] = R["ID"]
			msg["IP"] = self.request.remote_ip
			
			#Dumps the message and send it
			msg=json.dumps(msg)
			userIP[key].write_message(msg)
			print("Bullet Sent To: " + self.request.remote_ip)
			
def sendPositionToOtherPlayer(self, R):
	for key in userIP:
		if key != self.request.remote_ip:
			#Creates the dictionary
			msg=dict()
			
			#Populates the message
			msg["type"] = "Movement"
			msg["Pos"] = {"X":R["Pos"]["X"], "Y":R["Pos"]["Y"]}
			msg["ID"] = R["ID"]
			msg["Lifes"] = R["Lifes"]
			msg["IP"] = self.request.remote_ip
			
			#Dumps the message and send it
			message=json.dumps(msg)
			userIP[key].write_message(message)

def LevelComplete(self, R):
	for key in userIP:
			#Creates the dictionary
			msg=dict()
			
			#Populates the message
			msg["type"] = "Level_Complete"
			
			#Dumps the message and send it
			msg=json.dumps(msg)
			userIP[key].write_message(msg)

def ContinueToNextLevel(self,R):
	for key in userIP:
			#Creates the dictionary
			msg=dict()
			
			#Populates the message
			msg["type"] = "Continue_Next_Level"
			
			#Dumps the message and send it
			msg=json.dumps(msg)
			userIP[key].write_message(msg)
			
#Extends the tornado websocket handler
class WSHandler(tornado.websocket.WebSocketHandler):
	def check_origin(self,origin):
		return True

	def open(self):
		print("Websocket - Connection Opened")
		
	def on_close(self):
		print("Websocket - Connection Closed")

	def on_message(self, message):
		R = json.loads(message)
		if R['request'] == 'join':
			CheckJoin(self)
		if R['request'] == "Movement":
			sendPositionToOtherPlayer(self, R)
		if R['request'] == "Spawn Bullet":
			sendBulletToOtherPlayer(self, R)
		if R['request'] == "Level_Complete":
			LevelComplete(self, R)
		if R['request'] == "Continue_Next_Level":
			ContinueToNextLevel(self, R)

app = tornado.web.Application([
	(r'/test', WSHandler),
	])

if __name__ == '__main__':
	#what is 8080 ?
	app.listen(8080)
	tornado.ioloop.IOLoop.instance().start()
