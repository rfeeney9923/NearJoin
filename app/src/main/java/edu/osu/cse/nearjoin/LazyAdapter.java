package edu.osu.cse.nearjoin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.ohio_state.cse.nearjoin.eventbackend.myEvent.model.EventRecord;

public class LazyAdapter extends ArrayAdapter<EventRecord> {

    private Activity activity;
    private Context context;
    private List<EventRecord> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    int resource_id;

    public LazyAdapter(Context c, List<EventRecord> d) {
        super(c, R.layout.event_listrow, d);
        context = c;
        data = d;
        imageLoader=new ImageLoader(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.event_listrow, parent, false);

        TextView title = (TextView)vi.findViewById(R.id.title_post_event_editText); // title
        TextView time = (TextView)vi.findViewById(R.id.time_post_event_editText); // time
        TextView duration = (TextView)vi.findViewById(R.id.duration_post_event_editText); // duration
        TextView location = (TextView)vi.findViewById(R.id.location_post_event_editText); // location
        TextView participants = (TextView)vi.findViewById(R.id.eventParticipant_cell_listView);
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.host_image_cell_listView); // thumb image

        EventRecord event = getItem(position);
        if(event==null)
            return null;

        // Setting all values in listview
        title.setText(event.getTitle());
        time.setText(event.getStartDate());
        duration.setText(event.getEndDate());
        location.setText(event.getLocation());
        participants.setText(event.getParticipants().toString());
        imageLoader.DisplayImage(event.getHostUrl(), thumb_image);
        return vi;
    }

    public void setAdapterData(List<EventRecord> d)
    {
        data = d;
        notifyDataSetChanged();
    }
    public int getCount() {
        return data.size();
    }

    public EventRecord getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /*
    // add or update event
    public void updateList(EventRecord record) {
        if ((newDevice == null) || (newDevice.getAddress() == null)) {
            return;
        }

        boolean contains = false;
        for (Beacon device : mList) {
            if (newDevice.getAddress().equals(device.getDevice().getAddress())) {
                contains = true;
                device.setRssi(rssi);
                break;
            }
        }
        if (!contains) {
            //For readability we convert the bytes of the UUID into hex
            String UUIDHex = convertBytesToHex(Arrays.copyOfRange(scanRecord, 9, 25));
            if (UUIDHex.equals(GELO_UUID)) {
                //Bytes 25 and 26 of the advertisement packet represent the major value
                int major = (scanRecord[25] << 8) | (scanRecord[26] << 0);

                //Bytes 27 and 28 of the advertisement packet represent the minor value
                int minor = ((scanRecord[27] & 0xFF) << 8) | (scanRecord[28] & 0xFF);

                // add new BluetoothDevice
                mList.add(new Beacon(newDevice, UUIDHex, major, minor, rssi));
            }
        }

        // Notifies the attached observers that the underlying data has been changed
        // and any View reflecting the data set should refresh itself.
         notifyDataSetChanged();
    }
    */
}
