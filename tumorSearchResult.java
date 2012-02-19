public class tumorSearchResult
{
    private String _artist, _title, _tag;

    public tumorSearchResult(String artist, String title, String tag)
    {
	_artist = artist;
	_title = title ;
	_tag = tag ;
    }

    public tumorSearchResult()
    {
    }
    
    public void setArtist (String artist)
    {
	_artist = artist ;
    }
    
    public void setTitle (String title)
    {
	_title = title ;
    }

    public void setTag (String tag)
    {
	_tag = tag ;
    }

    public String getArtist()
    {
	return _artist ;
    }
    
    public String getTitle()
    {
	return _title ;
    }
    
    public String getTag()
    {
	return _tag ;
    }

    public String toString()
    {
	return _artist + " - " + _title ;
    }

}