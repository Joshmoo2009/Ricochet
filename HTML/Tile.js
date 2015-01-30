	function Tile(name, id, x, y, texture, w, h)
	{
		this.m_x = x;
		this.m_y = y;
		this.m_texture = texture;		
		this.m_name = name;
		this.m_id = id;
		this.t_width = w;
		this.t_height = h;
	}
	
	Tile.prototype.getName = function()
	{
		return this.m_name;
	}

	Tile.prototype.getID = function()
	{
		return this.m_id;
	}
	
	Tile.prototype.gettexture = function()
	{
		return this.m_texture;
	}

	Tile.prototype.draw = function()
	{
		game.ctx.drawImage(this.m_texture, this.m_x, this.m_y, this.t_width, this.t_height);
	}

