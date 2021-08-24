import javax.swing.event.DocumentEvent;

public interface DocumentListener extends javax.swing.event.DocumentListener
{
    void update(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e)
    {
        update(e);
    }

    @Override
    default void removeUpdate(DocumentEvent e)
    {
        update(e);
    }

    @Override
    default void changedUpdate(DocumentEvent e)
    {
        update(e);
    }
}
