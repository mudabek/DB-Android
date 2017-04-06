package edu.grinnell.appdev.grinnelldirectory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import edu.grinnell.appdev.grinnelldirectory.Model.Person;
import java.util.List;


public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

  private List<Person> mPersons;
  private Context mContext;


  public SearchResultsAdapter(Context context, List<Person> persons) {
    if (persons != null) {
      mPersons = persons;
      mContext = context;
    }
  }

  @Override
  public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(mContext);
    View personView = inflater.inflate(R.layout.item_searchresult, parent, false);
    return new ViewHolder(personView);
  }

  @Override public void onBindViewHolder(SearchResultsAdapter.ViewHolder viewHolder, int position) {
    Person person = mPersons.get(position);

    ImageView personImageView = viewHolder.personImage;
    TextView nameTextView = viewHolder.name;
    TextView majorTextView = viewHolder.major;
    TextView locationTextView = viewHolder.location;

    Picasso.with(mContext).load(person.getImgPath()).into(personImageView);
    nameTextView.setText(person.getFirstName() + " " + person.getLastName());
    majorTextView.setText(person.getMajor());
    locationTextView.setText(person.getAddress());
  }

  @Override public int getItemCount() {
    return mPersons == null ? 0 : mPersons.size();
  }

  /**
   * ViewHolder class to hold the views from the inflated layout for each list item
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView personImage;
    public TextView name;
    public TextView major;
    public TextView location;

    public ViewHolder(View itemView) {

      super(itemView);

      name = (TextView) itemView.findViewById(R.id.tv_name);
      major = (TextView) itemView.findViewById(R.id.tv_major);
      location = (TextView) itemView.findViewById(R.id.tv_location);
      personImage = (ImageView) itemView.findViewById(R.id.iv_personImage);
    }
  }
}
