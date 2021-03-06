/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FxmlFiles;

import DictionaryApplication.ClassGenerator;
import DictionaryApplication.Dictionary;
import DictionaryApplication.Fields;
import DictionaryApplication.Generator;
import java.io.IOException;
import UserClasses.superDictionary;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXMLNewObjectController implements Initializable {

    /**
     * Initializes the controller class.
     */
    List<TextField> attrField = new ArrayList<TextField>();
    List<Fields> objectFields = new ArrayList<Fields>();
   public static HashMap<String, superDictionary> pool = new HashMap<String, superDictionary>();
   public static HashMap<String,Dictionary> dictionaries=new HashMap<String,Dictionary>();
    @FXML
    private ComboBox chooseClass;
    @FXML
    private GridPane attributesPlace;
    @FXML
    public TextField key = new TextField();

    public ComboBox getc() {
        return chooseClass;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<String> fnameNew = Generator.getClassNames();
        fnameNew = Generator.getClassNames();

        chooseClass.getItems().addAll(fnameNew);
    }

    public void Back(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("FXMLNewClass.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        //app_stage.setTitle("Methods");
        app_stage.show();

    }

    public void createObject(ActionEvent event) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
//     Class<?> c=int.class;
//                
//                    c = Class.forName("UserClasses." + chooseClass.getValue().toString().trim());
//                
//        Field[] field = c.getDeclaredFields();
//        ArrayList<Class> params = new ArrayList<>();
//        
//        for(Field f: field)
//        {//System.out.println(f.getType());//.toString().substring(f.getType().toString().lastIndexOf(".")));
//            params.add(f.getType());//    System.out.println(f.getClass().);
//        }
//        Class[] paramsArr= params.toArray(new Class[params.size()]);
//        Constructor<?> cons;
//                System.out.println(params);
//                    cons = c. getConstructor(paramsArr);
//                    ArrayList<Object> b= new ArrayList<>();
//                
//                for (int i = 0; i < field.length; i++) {Object o=attrField.get(i).getText().trim();
//                    String fieldtype=field[i].getType().getSimpleName();
//                    switch(fieldtype){
//                        case "int":
//                                   o=Integer.parseInt(attrField.get(i).getText().trim());
//                        case "boolean":    
//                                    o=Boolean.parseBoolean(attrField.get(i).getText().trim());
//                        case "char": 
//                                    //o=Character.valueOf(fieldtype);
//                                    //o=Character.highSurrogate(Character.)
//                        case "double":
//                                    o=Double.parseDouble(attrField.get(i).getText().trim());
//                        case "float":
//                                    o=Float.parseFloat(attrField.get(i).getText().trim());
//                        case "short":
//                                    o=Short.parseShort(attrField.get(i).getText().trim());
//                        case "long":
//                                    o=Long.parseLong(attrField.get(i).getText().trim());
//                        case "byte":
//                                    o=Byte.parseByte(attrField.get(i).getText().trim());
//                    }
//                        b.add(o);
//                
//                Object[] bArr= b.toArray(new Object[b.size()]);
//                    System.out.println(bArr[0].toString());
//                    System.out.println(bArr.toString());
//               superDictionary bobject = (superDictionary) cons.newInstance(bArr);
//               pool.put(chooseClass.getValue().toString().trim() + "." + key.getText().trim(),bobject);
//                
//                    System.out.println(bobject.toString());
//                    System.out.println(pool.get(chooseClass.getValue().toString().trim() + "." + key.getText().trim()));
//                    System.out.println(pool.keySet().toString());
//               
//        }
        Class<?> c = int.class;
URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
            // Load the class from the classloader by name....

            //Files.move(Paths.get(tempSource), Paths.get(sourceClass), StandardCopyOption.REPLACE_EXISTING);
             c = classLoader.loadClass("UserClasses." + chooseClass.getValue().toString().trim());
            
       // c = Class.forName("UserClasses." + chooseClass.getValue().toString().trim());
        //Field[] field = c.getDeclaredFields();
        List<Field> field=ClassGenerator.getFields(c);
        ArrayList<Class> params = new ArrayList<>();
        String classKey=ClassGenerator.classKey("./attributes/" + chooseClass.getValue().toString().trim() + ".txt");
        String keyValue="";//System.out.println(classKey);
        for (Field f : field) {
            params.add(f.getType());
        }

        Class[] paramsArr = params.toArray(new Class[params.size()]);
        Constructor<?> cons;
        cons = c.getConstructor(paramsArr);
        ArrayList<Object> b = new ArrayList<>();
        
        for (int i = 0; i < field.size(); i++) {//System.out.println(field.get(i).getName().toString().trim());
        String value=   field.get(i).getName(); 
            //System.out.println(classKey + "  " + value);
        if(classKey.equals(value)){//System.out.println("hello");
                keyValue=objectFields.get(i).getKeyValue();
            }
            Object o = objectFields.get(i).getObject(field.get(i));
            b.add(o);
       }
        if(c.getDeclaredFields().length==0)
            keyValue="singleton";
        
        Object[] bArr = b.toArray(new Object[b.size()]);
        superDictionary bobject = (superDictionary) cons.newInstance(bArr);
        String tmp = chooseClass.getValue().toString().trim() + "." + keyValue;System.out.println(tmp);
        //System.out.println(tmp);
       // System.out.println(bobject);
        pool.put(tmp, bobject);
        // System.out.println(pool.keySet().toString());
        //               pool.put(chooseClass.getValue().toString().trim() + "." + key.getText().trim(),bobject);
        //System.out.println(pool.keySet().toString());
    }

    public void attributesFill(ActionEvent event) throws ClassNotFoundException, MalformedURLException {
        attributesPlace.getChildren().clear();
        
        attrField.clear();
        objectFields.clear();
//            List<String> fnameNew=Generator.getClassNames();
//                fnameNew=Generator.getClassNames();
//                 
//                 
        // attributesPlace.add(superClassMethod, 0, 0);
        String s = chooseClass.getValue().toString().trim();
        Class<?> c = int.class;
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
            
             c = classLoader.loadClass("UserClasses." + s);
            
            //c = Class.forName("UserClasses." + s);
            System.out.println(c.getSimpleName());
        } catch (ClassNotFoundException ex) {
            // Logger.getLogger(Projects.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error");
        }
//        attributesPlace.add(key, 1, 0);
//        attributesPlace.add(new Text("key"), 0, 0);
        //Field[] field = c.getDeclaredFields();
        System.out.println(c.getSimpleName());
        List<Field> field=ClassGenerator.getFields(c);
        int i;
        attributesPlace.add(new Text("AttributeType"), 0, 0);
        attributesPlace.add(new Text("AttributeName"), 1, 0);
        attributesPlace.add(new Text("AttributeValue"), 2, 0);
        List<String> classNames = Generator.getClassNames();
        for (i = 0; i < field.size(); i++) {
            attributesPlace.add(new Text(field.get(i).getName()), 1, i + 1);
            if (classNames.contains(field.get(i).getType().getSimpleName())) {
                ComboBox cc = new ComboBox();
                cc.getItems().addAll(Generator.getClassObject(field.get(i).getType().getSimpleName(), pool));
                objectFields.add(new Fields(cc));
                attributesPlace.add(cc, 2, i + 1);
            } else {
                TextField ccc = new TextField();
                objectFields.add(new Fields(ccc));
                attributesPlace.add(ccc, 2, i + 1);
            }
            attributesPlace.add(new Text(field.get(i).getType().getSimpleName()), 0, i + 1);
        }
        // z.add(method,1,i+1);
//            superClassMethod.getItems().clear();
        //      superClassMethod.getItems().addAll(fnameNew);    
    }

    public void classesAre(ActionEvent event) {

        chooseClass.getItems().clear();
        List<String> fnameNew = Generator.getClassNames();
        fnameNew = Generator.getClassNames();

        chooseClass.getItems().addAll(fnameNew);
    }
}
