import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel
{
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if(getColumnCount() > 0 && getRowCount() > 0)
        {
            return getValueAt(0, columnIndex).getClass();
        }
        else
        {
            return Object.class;
        }
    }
}
