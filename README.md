# My Personal Project: SleepMetrix

## Background

This project is intended to be a **comprehensive sleep
tracker** that tracks, analyzes, and visualizes sleep.
Some features include intake of sleep duration and sleep
quality, average sleep duration calculation, visual
presentation of sleep distribution, trends,
and comparison of sleep from month to month.
This application is intended to streamline the process
of recording sleep, helping individuals meet their sleeping goals.

### Goals of the Project

The goal of this project is to make sleep management
more **convenient and accessible**.
By providing insights into sleep habits, such as duration and
quality of sleep, the tracker aims to help users to make 
informed decisions about their sleep health.
Over time, users can track their progress towards sleep goals
and visualize trends in their sleep patterns, fostering motivation
and accountability. Integration with other health and wellness
apps allows for a holistic approach to promoting healthy sleep
habits.

### Target Users

This application mainly targets **individuals who struggle with 
sleep**, and those who aim to work on any **sleeping goals**.
This should be a **one-stop location** where
they can record sleep, as well as track their progress
over the months. Providing a visual representation and direct
comparisons should help users reach their intended sleep goals

### Why this Project?

I have always struggled with sleep, often having issues falling
asleep and dealing with low quality sleep. This became extremely
apparent in high school when I was enrolled in the full IB program,
a system known to be rigorous and demanding. I was working toward
two diplomas, my high school diploma, as well as my high school
diploma. As a result, I had to learn how to allot my time accordingly,
ensuring that I was being efficient.
Sleep played a big role in my performance. When I quickly realized
that I was struggling to have adequate sleep, I started tracking my
sleep duration and quality with the Health App on my phone.
However, I often found that the tracking was inaccurate, and I had
very little flexibility.
Therefore, this project allows for users to manually put in information,
making it extremely accurate and straight forward.

## User Stories
- As a user, I want to be able to add a night to my list of nights
- As a user, I want to be able to remove a night from my list of nights
- As a user, I want to be able to add sleep information such as
duration of sleep and quality of sleep to my nights
- As a user, I want to be able to view my list of nights with
sleep information
- As a user, I want to be able to view my average sleep duration
- As a user, I want to be able to view my total sleep duration
- As a user, I want to be able to view trends in my sleep
- As a user, I want to be able to save my list of nights
  (if I choose to do so)
- As a user, I want to be able to load my list of nights
  (if I choose to do so)

## Phase 4: Task 2
1. Initialization
   - The program initializes the user interface and loads sleep data log
2. User Interaction
   - User adds a new night's sleep data using the "Add Night" button
   - Program prompts for sleep duration, date, and quality, then updates the sleep tracker and UI
3. Data Persistence
   - User saves updated sleep log data by clicking "Save" which writes data to the JSON file
4. Viewing Statistics
   - User views sleep statistics by clicking "View Statistics", which displays total nights,
   total duration, and average sleep duration
5. Exiting the Program
   - User Exits the program, prompting a save option if changes were made

## Phase 4: Task 3
One improvement could be implementing the observer pattern to allow users to receive
notifications, enhancing flexibility and scalability. In addition, another improvement
could be made by splitting up the GUI to be multiple different classes in order
to improve readability, as well as efficiency. Lastly, this program can be improved
by including security enhancements such as encryption, authentication, and log ins
in order to protect user information.