package nba.util.component;

import javax.swing.JButton;

import nba.com.stat.Detail;
import nba.util.listener.PauseListener;

public class PauseButton extends JButton{
    Detail detail;
    

    public PauseButton(Detail detail){
        setDetail(detail);
        
        setText("PAUSE");

        addMouseListener(new PauseListener(detail, this));
    }
    
    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}
