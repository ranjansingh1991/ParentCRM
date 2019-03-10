package in.semicolonindia.parentcrm.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.parentcrm.R;
import in.semicolonindia.parentcrm.beans.BookListNames;
import in.semicolonindia.parentcrm.dialogs.RequestBookDialog;

/**
 * Created by Rupesh on 07-08-2017.
 */
@SuppressWarnings("ALL")
public class BookListListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<BookListNames> bookListNamesList = null;
    private ArrayList<BookListNames> arraylist;


    public BookListListAdapter(Context context, List<BookListNames> bookListNamesList) {
        this.context = context;
        this.bookListNamesList = bookListNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<BookListNames>();
        this.arraylist.addAll(bookListNamesList);
    }

    @Override
    public int getCount() {
        return bookListNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookListNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.book_list_list_item, null);
            holder.tvBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.btnRequestBook = (Button) convertView.findViewById(R.id.btnRequestBook);
            holder.tvAuthorName = (TextView) convertView.findViewById(R.id.tvAuthorName);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.tvClassName = (TextView) convertView.findViewById(R.id.tvClassName);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvBookName.setText(bookListNamesList.get(position).getName());
        holder.tvAuthorName.setText(bookListNamesList.get(position).getAuthor());
        holder.tvPrice.setText(context.getString(R.string.rupee_symbol) + bookListNamesList.get(position).getPrice() + "/-");
        holder.tvClassName.setText(bookListNamesList.get(position).getClassName());
        holder.tvDesp.setText(bookListNamesList.get(position).getDescription());


        holder.tvBookName.setTypeface(appFontRegular);
        holder.btnRequestBook.setTypeface(appFontRegular);
        holder.tvAuthorName.setTypeface(appFontLight);
        holder.tvPrice.setTypeface(appFontLight);
        holder.tvClassName.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);

        holder.btnRequestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBookDialog mRequestBookDialog = new RequestBookDialog(context, R.style.DialogSlideAnim,
                        bookListNamesList.get(position));
                mRequestBookDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mRequestBookDialog.getWindow().setGravity(Gravity.BOTTOM);
                mRequestBookDialog.show();
            }
        });

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bookListNamesList.clear();
        if (charText.length() == 0) {
            bookListNamesList.addAll(arraylist);
        } else {
            for (BookListNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    bookListNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvBookName;
        Button btnRequestBook;
        TextView tvAuthorName;
        TextView tvPrice;
        TextView tvClassName;
        TextView tvDesp;

    }
}
