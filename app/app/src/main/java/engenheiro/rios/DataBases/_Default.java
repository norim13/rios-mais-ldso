package engenheiro.rios.DataBases;

/**
 * Created by filipe on 28/10/2015.
 */
public class _Default {
    protected String _message;
    protected boolean _status;

    public _Default(){
        this._message="";
        this._status=true;
    }

    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public boolean is_status() {
        return _status;
    }

    public void set_status(boolean _status) {
        this._status = _status;
    }
}
