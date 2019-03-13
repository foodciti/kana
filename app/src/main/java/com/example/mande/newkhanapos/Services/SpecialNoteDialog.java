package com.example.mande.newkhanapos.Services;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.mande.newkhanapos.R;

public class SpecialNoteDialog extends DialogFragment {
    private TextView update_btn;
    private static int id = 1;
    public static EditText customername,customeraddress;
    private SpecialNodeListener mListener;
    AutoCompleteTextView customertelephone;
    public static AutoCompleteTextView customerpostcode;
    private ImageView closebtn;
    public static boolean orderupdatestastus=false;
    public static boolean calleridstatus=false;
    String value;
    SharedPreferences.Editor editor;

    SharedPreferences shared;

    String where_click="";
  //  private RecyclerView rvKeyList;
  //  private static String inputList[] = {"0","1","2","3","4","5","6","7","8","9","×","Q","W","E","R","T","Y","U","I","O","P","SP","A","S","D","F","G","H","J","K","L","↑","↓","","","X","C","V","B","N","M"};


  //  private MyAdapter mAdapter;

    public static SpecialNoteDialog getInstance(boolean verify ) {
        SpecialNoteDialog addition = new SpecialNoteDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean("VERIFY",verify);
        addition.setArguments(bundle);
        return addition;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.special_note, container,
                false);

      //  shared= getContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


      //  rvKeyList = (RecyclerView)rootView.findViewById(R.id.rv_keylist);

      //  GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 11);
     //   rvKeyList.setLayoutManager(layoutManager);

     //   rvKeyList.setHasFixedSize(true);


    //    mAdapter = new MyAdapter(inputList);
     //   rvKeyList.setAdapter(mAdapter);

       /* update_btn=(TextView) rootView.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (!customername.getText().toString().isEmpty()){

              //   special_note_text.setText(customername.getText().toString().trim());


                 mListener.returnSpecialNotes(customername.getText().toString().trim());
               dismiss();
             }
            }
        });*/

        closebtn=(ImageView) rootView.findViewById(R.id.close);
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        customername=(EditText) rootView.findViewById(R.id.housename);


       /* rootView.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("1");
            }
        });

        rootView.findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("2");
            }
        });

        rootView.findViewById(R.id.three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("3");
            }
        });

        rootView.findViewById(R.id.four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("4");
            }
        });

        rootView.findViewById(R.id.fifth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("5");
            }
        });

        rootView.findViewById(R.id.six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("6");
            }
        });

        rootView.findViewById(R.id.seven).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("7");
            }
        });

        rootView.findViewById(R.id.eight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("8");
            }
        });

        rootView.findViewById(R.id.ninth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("9");
            }
        });

        rootView.findViewById(R.id.devide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("TAB");
            }
        });

        rootView.findViewById(R.id.zero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("0");
            }
        });

        rootView.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("1");
            }
        });

        rootView.findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("1");
            }
        });

              rootView.findViewById(R.id.multiply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("×");
            }
        });*/



        rootView.findViewById(R.id.sp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("SP");
            }
        });

        rootView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("×");
            }
        });



        rootView.findViewById(R.id.q).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("Q");
            }
        });

        rootView.findViewById(R.id.w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("W");
            }
        });

        rootView.findViewById(R.id.e).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("E");
            }
        });

        rootView.findViewById(R.id.r).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("R");
            }
        });

        rootView.findViewById(R.id.t).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("T");
            }
        });

        rootView.findViewById(R.id.y).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("Y");
            }
        });

        rootView.findViewById(R.id.u).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("U");
            }
        });

        rootView.findViewById(R.id.i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("I");
            }
        });

        rootView.findViewById(R.id.o).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("O");
            }
        });

        rootView.findViewById(R.id.p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("P");
            }
        });

        rootView.findViewById(R.id.a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("A");
            }
        });

        rootView.findViewById(R.id.s).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("S");
            }
        });

        rootView.findViewById(R.id.d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("D");
            }
        });

        rootView.findViewById(R.id.f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("F");
            }
        });

        rootView.findViewById(R.id.g).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("G");
            }
        });

        rootView.findViewById(R.id.h).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("H");
            }
        });

        rootView.findViewById(R.id.j).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("J");
            }
        });

        rootView.findViewById(R.id.k).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("K");
            }
        });

        rootView.findViewById(R.id.l).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("L");
            }
        });

        rootView.findViewById(R.id.m).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("M");
            }
        });

        rootView.findViewById(R.id.z).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("Z");
            }
        });

        rootView.findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("X");
            }
        });

        rootView.findViewById(R.id.c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("C");
            }
        });

        rootView.findViewById(R.id.v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("V");
            }
        });

        rootView.findViewById(R.id.b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("B");
            }
        });

        rootView.findViewById(R.id.n).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("N");
            }
        });
        rootView.findViewById(R.id.at).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  setValue("@");

                setValue("CL");
            }
        });
        rootView.findViewById(R.id.dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue(".");
            }
        });
        rootView.findViewById(R.id.pound_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("£");
            }
        });
        rootView.findViewById(R.id.question_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("?");
            }
        });
        rootView.findViewById(R.id.dash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("-");
            }
        });
        rootView.findViewById(R.id.plus_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("+");
            }
        });

        rootView.findViewById(R.id.one1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("1");
            }
        });

        rootView.findViewById(R.id.two2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("2");
            }
        });

        rootView.findViewById(R.id.three3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("3");
            }
        });

        rootView.findViewById(R.id.four4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("4");
            }
        });

        rootView.findViewById(R.id.five5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("5");
            }
        });

        rootView.findViewById(R.id.six6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("6");
            }
        });

        rootView.findViewById(R.id.seven7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("7");
            }
        });

        rootView.findViewById(R.id.eight8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("8");
            }
        });

        rootView.findViewById(R.id.nine9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("9");
            }
        });

        rootView.findViewById(R.id.zero1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValue("0");
            }
        });

        rootView.findViewById(R.id.enterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customername.getText().toString().length()==0){

                }else{
                    mListener.returnSpecialNotes(customername.getText().toString().trim());
                    dismiss();
                }

            }
        });

        customername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                /*InputMethodManager mgr = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(customeraddress.getWindowToken(), 0);*/
                final InputMethodManager imm=(InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(customername.getWindowToken(),0);

               // view.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                customername.requestFocus();
               // customername.setSelection(customername.getText().toString().length());

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Layout layout = ((TextView) view).getLayout();
                    int x = (int)motionEvent.getX();
                    int y = (int)motionEvent.getY();
                    if (layout!=null){
                        int line = layout.getLineForVertical(y);
                        int offset = layout.getOffsetForHorizontal(line, x);
                        Log.v("index", ""+offset);

                        //Toast.makeText(getContext(),""+offset,Toast.LENGTH_LONG).show();
                        customername.setSelection(offset);
                    }
                }

                return true;
            }
        });
        return rootView;
    }

    private void setValue(String s) {

        if(s.toString().equalsIgnoreCase("×")){
            if(customername.getText().toString().length()>0){
                String value=customername.getText().toString();
                StringBuilder sb = new StringBuilder(value);
                // Toast.makeText(getContext(),""+remark.getSelectionStart(),Toast.LENGTH_LONG).show();
                int i=customername.getSelectionStart();
                if(i>0){
                    sb = sb.deleteCharAt(customername.getSelectionStart()-1);
                    customername.setText(sb);

                    customername.setSelection(i-1);
                }


            }
        }else if(s.toString().equalsIgnoreCase("SP")) {

            customername.getText().insert(customername.getSelectionStart(), " ");
        }else if(s.toString().equalsIgnoreCase("CL")) {
            customername.setText("");
        } else{
            customername.getText().insert(customername.getSelectionStart(), s.toString());
        }
        customername.setSelection(customername.getSelectionStart());

            //  Toast.makeText(getContext(),""+datas[position].toString(),Toast.LENGTH_LONG).show();
        }



    public interface SpecialNodeListener {
        void returnSpecialNotes(String returnbackpound);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close the Realm instance.

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private boolean initFlag = true;
        public String[] datas = null;

        public MyAdapter(String[] datas) {
            this.datas = datas;
        }

        //创建新View
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.input_board_item, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);



            return vh;
        }

        //将数据与界面进行绑定
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.mTextView.setText(datas[position]);

            //将数据保存在itemView的Tag中，以便点击时进行获取
            viewHolder.itemView.setTag(datas[position]);
            if(initFlag && position == 0){
                initFlag = false;
                viewHolder.itemView.requestFocus();
            }

            if(datas[position].toString().equalsIgnoreCase("")){
                viewHolder.mTextView.setVisibility(View.GONE);
                viewHolder.main_layout.setVisibility(View.GONE);
            }

            viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(datas[position].toString().equalsIgnoreCase("×")){
                        if(customername.getText().toString().length()>0){
                            String value=customername.getText().toString();
                                /*char what=value.charAt(value.length() - 1);
                                String strNew = value.replace(""+what, "");*/

                            StringBuilder sb = new StringBuilder(value);
                            sb = sb.deleteCharAt(customername.getText().toString().length()-1);

                            customername.setText(sb);
                        }
                    }else if(datas[position].toString().equalsIgnoreCase("SP")) {
                        String value=customername.getText().toString();
                        customername.setText(value+" ");
                    } else
                    {
                        String value=customername.getText().toString();
                        customername.setText(value+""+datas[position].toString());
                    }
                    customername.setSelection(customername.getText().toString().length());

                     //   String value=customername.getText().toString();
                    // customername.setText(value+""+datas[position].toString());
                        // Toast.makeText(getContext(),"Not Enough Amount",Toast.LENGTH_LONG).show();

                }
            });

        }

        //获取数据数量
        @Override
        public int getItemCount() {
            return datas.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public RelativeLayout main_layout;

            public ViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.tv_textInput);
                main_layout=(RelativeLayout) view.findViewById(R.id.main_layout);
            }
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.99);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.98);

            dialog.getWindow().setLayout(width, height);
        }
    }

    public int getScreenWidth(Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size.x;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SpecialNodeListener) {
            mListener = (SpecialNoteDialog.SpecialNodeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
