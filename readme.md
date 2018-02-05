SolarexProgressDemo
-----------------------------

```
attrs.xml
    <declare-styleable name="CommericalProgressbar">
        <attr name="cpb_background" format="color"/>
        <attr name="cpb_foreground" format="color"/>
        <attr name="cpb_max" format="integer"/>
        <attr name="cpb_progress" format="integer"/>
        <attr name="cpb_secondprogress" format="integer"/>
        <attr name="cpb_secondprogresscolor" format="color"/>
    </declare-styleable>
    <declare-styleable name="RectProgressbar">
        <attr name="rpb_background" format="color"/>
        <attr name="rpb_foreground" format="color"/>
        <attr name="rpb_max" format="integer"/>
        <attr name="rpb_progress" format="integer"/>
        <attr name="rpb_gap" format="integer"/>
        <attr name="rpb_rectnum" format="integer"/>
    </declare-styleable>
```

使用

```
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:background="#171c26"
    tools:context="com.solarexsoft.solarexprogressdemo.MainActivity"
    android:orientation="vertical">


    <com.solarexsoft.solarexprogress.CommericalProgressbar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
    <com.solarexsoft.solarexprogress.RectProgressbar
        android:layout_marginTop="50dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>


</LinearLayout>
```

