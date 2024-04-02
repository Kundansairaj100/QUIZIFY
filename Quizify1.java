import java.util.*;
// INTERFACES
interface Review
{
	public void Report(User_Account cur_user);
}
//EXCEPTIONS
class Aldready_Attempted extends Exception
{
    public String toString()
    {
        return "QUIZ ALDREADY ATTEMPTED!";
    }
}
class Valid_Email extends Exception
{
    public String toString()
    {
        return "Not a Valid Email-id!";
    }
}
//CLASSES
//----------------------SUBJECTS-----------------------------------------
class Subjects
{
    Scanner s = new Scanner(System.in);
    public String Subject_Name;
    String []  Questions;
    String [] Answers;
    int Answer_weight;
    int Max_Score;
    int time;
    int qnum;
    public Subjects(String Name)
    {
        Subject_Name = Name;
        Questions = new String[100];
        Answers = new String[100];
        Answer_weight = 0;
        Max_Score=0;
        time = 0;
        qnum = 0;
    }
    public void Quiz_Making()
    {
        System.out.println("Making "+Subject_Name+"'s Quiz");
        System.out.print("Marks for Each Answer: ");
        Answer_weight = s.nextInt();
        System.out.println("Enter time limit");
        time = s.nextInt();
        System.out.println("Select quiz type:");
        System.out.println("1. MCQ \n2. Fill in the blanks");
        int c_1 = s.nextInt();
        switch(c_1)
        {
            case 1:
                MCQ();
                break;
            case 2:
                FIB();
                break;
        }
    }
    public void FIB()
    {
        System.out.print("Enter the Number of Question: ");
        qnum = s.nextInt();

        Max_Score = Answer_weight*qnum;
        for(int i=0;i<qnum;i++)
        {
            s.nextLine();
            System.out.print("Enter the Question: ");
            String question = s.nextLine();
            Questions[i] = question;
            System.out.print("Enter the Answer: ");
            String answer = s.nextLine();
            Answers[i] = answer;
        }
        System.out.println("----------------------------------------------");
        System.out.println("QUIZ FORMED: ");

        
        System.out.println("----------------------------------------------");
        System.out.println("All the Questions: ");
        for(int i=0;i<qnum;i++)
        {
            System.out.println("> "+Questions[i]);
        }
        System.out.println("----------------------------------------------");
        System.out.println("All the Answers: ");
        for(int i=0;i<qnum;i++)
        {
            System.out.println("> "+Answers[i]);
        }
        System.out.println("----------------------------------------------");
    }
    public void MCQ()
    {
        System.out.print("Enter the Number of Question: ");
        qnum = s.nextInt();

        Max_Score = Answer_weight*qnum;
        for(int i=0;i<qnum;i++)
        {
            s.nextLine();
            System.out.print("Enter the Question: ");
            String question = s.nextLine();
            System.out.println("Option 1:");
            String o1 = s.nextLine();
            System.out.println("Option 2:");
            String o2 = s.nextLine();
            System.out.println("Option 3:");
            String o3 = s.nextLine();
            System.out.println("Option 4:");
            String o4 = s.nextLine();
            Questions[i] = question + " Option 1:" + o1 + " Option 2:" + o2 + " Option 3:" + o3 + " Option 4:" + o4;
            System.out.print("Enter the Answer: ");
            String answer = s.nextLine();
            Answers[i] = answer;
        }
        System.out.println("----------------------------------------------");
        System.out.println("QUIZ FORMED: ");
        System.out.println("----------------------------------------------");
        System.out.println("All the Questions: ");
        for(int i = 0; i < qnum; i++)
        {
            System.out.println("> "+ Questions[i]);

        }
        System.out.println("----------------------------------------------");
        System.out.println("All the Answers: ");
        for(int i = 0; i < qnum; i++)
        {
            System.out.println("> "+ Answers[i]);
        }
        System.out.println("----------------------------------------------");
    }
        
    }

//--------------------------SUBJECTS-----------------------------------------------
//--------------------------ACCOUNTS------------------------------------------------
class User_Account
{
    Scanner s = new Scanner(System.in);
    public String Name;
    public String Password;
    public String Emailid;
    ArrayList<Integer> Marks;
    ArrayList<String> Subjects_Attempted;
    public User_Account(String Name, String Password,String email)
    {
        this.Name = Name;
        this.Password = Password;
        this.Emailid = email;
        Marks = new ArrayList<Integer>();
        Subjects_Attempted = new ArrayList<String>();
    }
}
//--------------------------------------ACCOUNTS-----------------------------------
//--------------------------------------QUIZ ATTEMPT-------------------------------
class Quiz_Attempt 
{
    Scanner s = new Scanner(System.in);
    public void QuizAttempt(Subjects sub, User_Account cur_user) {
        ArrayList<String> Student_Answer = new ArrayList<>();
        System.out.println("----------------------------------------------");
        System.out.println("Attempting " + sub.Subject_Name + "'s Quiz");

        Timer timer = new Timer();
        TimerTask task = new TimerTask() 
        {
            int sec = 0;
            public void run() 
            {
                if (sec >= sub.time) {
                    System.out.println("----------------------------------------------");
                    System.out.println("Time's up! Quiz finished.");
                    System.out.println("----------------------------------------------");
                    timer.cancel();
                    evaluateQuiz(sub, Student_Answer, cur_user);
                } 
                else 
                {
                    sec++;
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
        for (int i = 0; i < sub.qnum; i++) {
            System.out.println("Q: " + sub.Questions[i]);
            System.out.print("Enter answer in sentence or word as given: ");
            String ans = s.nextLine();
            Student_Answer.add(ans);
        }
        timer.cancel();
        evaluateQuiz(sub, Student_Answer, cur_user);
        System.out.println("QUIZ FEEDBACK");
        System.out.println("----------------------------------------------");
        System.out.println("Your Answers: ");
        for(String i: Student_Answer)
        {
            System.out.println("> "+i);
        }
        System.out.println("----------------------------------------------");
        System.out.println("Correct Ans: ");
        for(int i=0;i<sub.qnum;i++)
        {
            System.out.println("> "+sub.Answers[i]);
        }
        System.out.println("----------------------------------------------"); 
    }

    private void evaluateQuiz(Subjects sub, ArrayList<String> studentAnswers, User_Account cur_user) {
        int Score = 0;

        for (int i = 0; i < sub.qnum; i++) {
            if (studentAnswers.contains(sub.Answers[i])) {
                Score += sub.Answer_weight;
            }
        }

        System.out.println("----------------------------------------------");
        System.out.println("MARKS SECURED: " + Score + " Marks");
        System.out.println("RESULT: " + Score + "/" + sub.Max_Score);
        System.out.println("----------------------------------------------");
        System.out.println("----------------------------------------------");
        cur_user.Marks.add(Score);
        cur_user.Subjects_Attempted.add(sub.Subject_Name);
    }
}

//--------------------------------------QUIZ ATTEMPT-----------------------------------
//--------------------------------------REVIEW-------------------------------------
class Marks_Review implements Review 
{
    
    public void Report(User_Account cur_user)
    {
        if(cur_user.Marks!=null)
        {
            System.out.println("Quiz Reports of: "+cur_user.Name);
            System.out.println("SUBJECTS ATTEMPTED: ");
            System.out.println(cur_user.Subjects_Attempted);
            System.out.println("Marks Secured in Each Quiz Respectively: ");
            System.out.println(cur_user.Marks);
            System.out.println("----------------------------------------------");
        }
        else
        {
            System.out.println("NO QUIZ ATTEMPTED TILL NOW!");
            System.out.println("----------------------------------------------");
        }
    }
}
//---------------------------------REVIEW------------------------------------------
//------------------------------MAIN FUNCTION--------------------------------------
public class Quizify1
{
    public static void main(String[] args)
    {
        Boolean use1 = true;
        Scanner s = new Scanner(System.in);
        System.out.println("----------------------------------------------");
        System.out.println("WELCOME TO QUIZIFY!");
        System.out.println("----------------------------------------------");
        //Instance Variables
        int present = 0;
        String Name;
        String Password;
        String email="";
        User_Account curent_user = null;
        Subjects current_Subject = null;
        // Classes Objects Creation
        Quiz_Attempt qa = new Quiz_Attempt();
        Marks_Review mr = new Marks_Review();
        // Array Lists
        ArrayList<User_Account> new_Student = new ArrayList<User_Account>();
        ArrayList<User_Account> new_Teacher = new ArrayList<User_Account>();
        ArrayList<String> Email_List = new ArrayList<String>();
        ArrayList<Subjects> Subjects_List = new ArrayList<Subjects>(); 
        ArrayList<String> Subjects_List_String = new ArrayList<String>();
        while(use1.equals(true))
        {
            Boolean use = true;
            System.out.println("Enter 1 for Teacher \nEnter 2 for Students");
            System.out.print("Enter: ");
            int access = s.nextInt();
            System.out.println("----------------------------------------------");
            System.out.println("Enter 1 to Sign-Up \nEnter 2 to Log-In");
            System.out.print("Enter: ");
            int n = s.nextInt();
            switch(n)
            {
                case 1:
                    System.out.println("----------------------------------------------");
                    System.out.println("ACCOUNT CREATION: ");
                    switch(access)
                    {
                        case 1:
                            System.out.print("Enter User Name: ");
                            Name = s.next();
                            System.out.print("Enter Password: ");
                            Password = s.next();
                            try
                            {
                                    System.out.print("Enter email id: ");
                                    email = s.next();
                                    if(email.contains("@gmail.com"))
                                    {
                                        System.out.println("Valid Email id");
                                        Email_List.add(email);
                                    }
                                    else
                                    {
                                        throw new Valid_Email();
                                    }

                            }
                            catch(Exception e)
                            {
                                System.out.println(e);
                            }
                            User_Account newTeacher = new User_Account(Name, Password,email);
                            new_Teacher.add(newTeacher);
                            curent_user = newTeacher;
                            break;
                        case 2:
                            System.out.print("Enter User Name: ");
                            Name = s.next();
                            System.out.print("Enter Password: ");
                            Password = s.next();
                            try
                            {
                                    System.out.print("Enter email id: ");
                                    email = s.next();
                                    if(email.contains("@gmail.com"))
                                    {
                                        System.out.println("Valid Email id");
                                        User_Account newUser = new User_Account(Name, Password,email);
                                        new_Student.add(newUser);
                                        curent_user = newUser;
                                        Email_List.add(email);
                                    }
                                    else
                                    {   
                                        throw new Valid_Email();
                                    }
                            }
                            catch(Exception e)
                            {
                                System.out.println(e);
                            }
                            break;
                    }
                    System.out.println("----------------------------------------------");
                    break;
                case 2:
                    System.out.println("----------------------------------------------");
                    System.out.println("LOGGING IN: ");
                    switch(access)
                    {
                        case 1:
                            System.out.print("Enter User Name: ");
                            Name = s.next();
                            System.out.print("Enter Password: ");
                            Password = s.next();
                            for(User_Account i: new_Teacher)
                            {
                                if((i.Name).equals(Name) & (i.Password).equals(Password))
                                {
                                    present = 1;
                                    curent_user = i;
                                    break;
                                }
                            }
                            if(present==1)
                            {
                                System.out.println("Login Successfull!");
                                System.out.println("Welcome back! "+curent_user.Name);
                            }
                            else
                            {
                                System.out.println("Login Falied! No Account Exists!");
                                use = false;
                            }
                            break;
                        case 2:
                            System.out.println("Students Login: ");
                            System.out.print("Enter User Name: ");
                            Name = s.next();
                            System.out.print("Enter Password: ");
                            Password = s.next();
                            for(User_Account i: new_Student)
                            {
                                if(Name.equals(i.Name) & Password.equals(i.Password))
                                {
                                    present = 1;
                                    curent_user = i;
                                    break;
                                }
                            }
                            if(present==1)
                            {
                                System.out.println("Login Successfull!");
                                System.out.println("Welcome back! "+curent_user.Name);
                            }
                            else
                            {
                                System.out.println("Login Falied! No Account Exists!");
                                use = false;
                            }
                        }
                        System.out.println("----------------------------------------------");
                        break;
                default:
                    System.out.println("Invalid Entry!");
                    use = false;
                    break;
            }
            System.out.println("----------------------------------------------");
            switch(access)
            {
                case 1:
                    while(use.equals(true))
                    {
                        System.out.println("TEACHERS DASHBOARD");
                        System.out.println("----------------------------------------------");
                        System.out.println("Enter 1 to Access Data-Base \nEnter 2 to Add Subjects \nEnter 3 to Remove Subjects \nEnter 4 to Make a Quiz \nEnter 5 to Exit");
                        System.out.print("Enter: ");
                        int t = s.nextInt();
                        System.out.println("----------------------------------------------");
                        switch(t)
                        {
                            case 1:
                                System.out.println("---------ACCESSING DATABASE----------");
                                System.out.println("STUDENTS DETAILS: ");
                                for(User_Account i: new_Student)
                                {
                                    System.out.println("> "+i.Name);
                                }
                                System.out.println("TEACHERS DETAILS: ");
                                for(User_Account i: new_Teacher)
                                {
                                    System.out.println("> "+i.Name);
                                }
                                System.out.println("----------------------------------------------");
                                break;
                            case 2:
                                System.out.println("----------SUBJECT ADDITION--------------------");
                                System.out.print("Enter the Subject Name: ");
                                String subname = s.next();
                                Subjects Sub = new Subjects(subname);
                                Subjects_List.add(Sub);
                                Subjects_List_String.add(subname);
                                System.out.println("The Subjects List: ");
                                System.out.println(Subjects_List_String);
                                System.out.println("Edited By: "+curent_user.Name);
                                System.out.println("----------------------------------------------");
                                break;
                            case 3:
                                System.out.println("----------SUBJECT MODIFICATION----------------");
                                System.out.print("Enter the Subject to be Removed: ");
                                subname = s.next();
                                int sub_rem_op=0;
                                for(Subjects subit: Subjects_List)
                                {
                                    if(subname.equals(subit.Subject_Name))
                                    {
                                        Subjects_List.remove(subit);
                                        Subjects_List_String.remove(subname);
                                        System.out.println("The Subjects List: ");
                                        System.out.println(Subjects_List_String);
                                        System.out.println("Edited By: "+curent_user.Name);
                                        sub_rem_op = 1;
                                        System.out.println("----------------------------------------------");
                                    }
                                }
                                if(sub_rem_op==0)
                                {
                                    System.out.println("Subject Dosen't Exsist");
                                    System.out.println("----------------------------------------------");
                                }
                                break;
                            case 4:
                                System.out.println("-----------QUIZ-------------------------------");
                                System.out.print("The Subjects: ");
                                System.out.println(Subjects_List_String);
                                System.out.println("----------------------------------------------");
                                System.out.print("Enter the Subject for Quiz Making: ");
                                subname = s.next();
                                int sub_crit=0;
                                for(Subjects i: Subjects_List)
                                    {
                                        if(subname.equals(i.Subject_Name))
                                        {
                                            System.out.println("----------------------------------------------");
                                            current_Subject = i;
                                            current_Subject.Quiz_Making();
                                            sub_crit=1;

                                        }
                                    }
                                    if(sub_crit==0)
                                    {
                                        System.out.println("Subject Dosen't Exsist");
                                        System.out.println("----------------------------------------------");
                                    }

                                break;
                            case 5:
                                System.out.println("Exiting Teachers DashBoard!");
                                System.out.println("----------------------------------------------");
                                curent_user = null;
                                use=false;
                                break;
                            default:
                                System.out.println("Wrong-Choice!");    
                        }
                    }
                    break;
                case 2:
                    
                    while(use.equals(true))
                    {
                        System.out.println("STUDENTS DASHBOARD");
                        System.out.println("----------------------------------------------");
                        System.out.println("Enter 1 for Attempting a Quiz \nEnter 2 for Viewing Marks & Progress \nEnter 3 for Viewing Subjects \nEnter 4 for Exiting  ");
                        System.out.print("Enter: ");
                        n = s.nextInt();
                        System.out.println("----------------------------------------------");
                        switch(n)
                        {
                            case 1:
                                System.out.println("QUIZ SECTION: ");
                                System.out.println("SUBJECTS: ");
                                System.out.println(Subjects_List_String);
                                System.out.println("----------------------------------------------");
                                System.out.print("Enter the Subject to Attempt it: ");
                                String subname = s.next();
                                int sub_crit=0;
                                for(Subjects i: Subjects_List)
                                {
                                    try
                                    {
                                        if(curent_user.Subjects_Attempted.contains(subname))
                                        {
                                            sub_crit=1;
                                            throw new Aldready_Attempted();
                                        }
                                        else if(subname.equals(i.Subject_Name))
                                        {
                                            qa.QuizAttempt(i,curent_user);
                                            sub_crit=1;
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        System.out.println(e);
                                    }
                                }
                                if(sub_crit==0)
                                {
                                    System.out.println("Subject Not Found!");
                                }
                                break;
                            case 2:
                                System.out.println("MARKS & PROGRESS SECTION: "); 
                                mr.Report(curent_user);
                                break;
                            case 3:
                                System.out.println("SUBJECTS: ");
                                System.out.println(Subjects_List_String);
                                System.out.println("----------------------------------------------");
                                break;
                            case 4:
                                System.out.println("Exiting Student DashBoard!");
                                curent_user = null;
                                use=false;
                                break;
                            default:
                                System.out.println("Invalid Entry!");
                                break;
                        }
                    }
                    System.out.println("----------------------------------------------");
                    break;
                default:
                    System.out.println("WRONG-ENTRY!");
                    System.out.println("----------------------------------------------");
                    break;
            }
            System.out.println("Enter 1 to Continue \nEnter 2 to Exit");
            System.out.print("Enter: ");
            int usemain = s.nextInt();
            if(usemain==2)
            {
                use1=false;
                System.out.println("----------------------------------------------");
                System.out.println("ThankYou for Using QUIZIFY!");
                System.out.println("----------------------------------------------");
            }
            else
            {
                use1=true;
                System.out.println("----------------------------------------------");
            }
        }
        s.close();
    }
}