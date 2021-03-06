function Player(Scale, CANVAS_WIDTH, CANVAS_HEIGHT)//Base Class for Player and Enemy to inherate from
{
	//Box2D Variables
	this.fixDef = new b2FixtureDef;
	this.fixDef.density = 1.0;
	this.fixDef.friction = 0.5;
	this.fixDef.restitution = 0.2;
	this.bodyDef = new b2BodyDef;
	this.bodyDef.type = b2Body.b2_dynamicBody;
	// positions the center of the object (not upper left!)
	this.bodyDef.position.x = CANVAS_WIDTH / 2 / Scale;
	this.bodyDef.position.y = CANVAS_HEIGHT / Scale;
	this.fixDef.shape = new b2CircleShape(35.5);
	this.Body = game.world.CreateBody(this.bodyDef).CreateFixture(this.fixDef);

	this.ai = false;//can be used to juge if this is player or enemy
	this.m_x = 20;
	this.m_y = 200;
	this.m_width = 20;
	this.m_height = 200;
	this.speed = 10;
	var coin = sprite({
    	context: game.ctx,
    	width: 25,
   		height: 26,
    	image: this.Sprite
	});
	this.animationPosX = 0;
	
	this.request = "join"
}

function sprite (options) {
				
    var that = {};
					
    that.context = options.context;
    that.width = options.width;
    that.height = options.height;
    that.image = options.image;

    return that;
}

Player.prototype.Move = function(e)
{
	/*console.log(this);*/
	if(this.ai == false)
	{
		if(e.keyCode == 38) // Up Key
			this.m_y -= this.speed;
		if(e.keyCode == 40) // Down Key
			this.m_y += this.speed;
	}
	/*console.log("Move called");*/
}

Player.prototype.Update = function()
{
	if(this.animationPosX == 0)
		this.animationPosX = 26;
	else if(this.animationPosX == 26)
		this.animationPosX = 53;
	else if(this.animationPosX == 53)
		this.animationPosX = 81;
	else 
		this.animationPosX = 0;
}

Player.prototype.AIMovement = function()
{

}

Player.prototype.Draw = function(id)
{
	/*console.log("Draw called");*/
	this.pos = this.Body.GetBody().GetPosition();
	if(id == 0)
		game.ctx.drawImage(resourceManager.player1Sprite, this.animationPosX, 0, 25, 26, this.pos.x, this.pos.y, 25, 26)
	else
		game.ctx.drawImage(resourceManager.player2Sprite, this.animationPosX, 0, 25, 26, this.pos.x, this.pos.y, 25, 26)
	//game.ctx.drawImage(this.Sprite, this.pos.x / 32.5, this.pos.y / 32.5, 25,26);
}

Player.prototype.MoveToPosition(x, y)
{
	this.m_x = x;
	this.m_y = y;
}

Player.prototype.GetWidth = function()
{
	return this.m_width;
}

Player.prototype.GetHeight = function()
{
	return this.m_height;
}
Player.prototype.GetX = function()
{
	return this.m_x;
}
Player.prototype.GetY = function()
{
	return this.m_y;
}
Player.prototype.Speed = function()
{
	return this.speed;
}
Player.prototype.GetFixDef = function()
{
	return this.fixDef;
}
Player.prototype.GetBodyDef = function()
{
	return this.bodyDef;
}
Player.prototype.GetLives = function()
{
	return this.lives;
}
Player.prototype.GetScore = function()
{
	return this.score;
}