# quiz website 


Question Types
All teams must support the following question types:
Question-Response—This is a standard text question with an appropriate text response. For example: Who was President during the Bay of Pigs fiasco?
Fill in the Blank—This is similar to standard Question-Response, except a blank can go anywhere within a question. For example: “One of President Lincoln’s most famous speeches was the __________ Address.”
Multiple Choice—Allow the user to select from one of a number of possible provided answers. Please present multiple-choice questions using radio buttons—this should not be treated as a Question-Response question where the user enters an “A”, “B”, or “C” into a blank textfield.
2
Picture-Response Questions—In a picture response question, the system will display an image, and the user will provide a text response to the image. Here are some examples of picture-response questions. The system displays an image of a bird, the user responds with the name of the bird species; the system displays an image of a US President, the user responds with the name of the president; the system displays a chemical structure of a molecule, the user responds with the name of the molecule.
To keep things simple, you may use absolute URLs to external images as the source of your images, instead of allowing the user to upload images to your server when creating a picture-response questions. For example a quiz on buildings at Stanford would serve up:
http://events.stanford.edu/events/252/25201/Memchu_small.jpg
rather than serving up a copy of the image stored on your test server.
Your system should be designed to allow easy introduction of new question types. Here are some straight-forward question types you can consider adding as extensions. Even if you don’t use these extensions, adding them should not require reworking of any of your existing code (although it will, of course, require you to write additional code).
Multi-Answer Questions—This is similar to the standard question-response, except there needs to be more than one text field for responses. Allow the quiz creator to determine if the responses need to be in a particular order or not. For example, list the 50 states in the US (order not relevant) or list the 10 most populous countries in the world in order from largest to smallest.
Multiple Choice with Multiple Answers—This is similar to a standard multiple choice question, except the user can select more than one response. For example: please mark each statement below which is true (1) Stanford was established in 1891, (2) Stanford has the best computer science department in the world, (3) Stanford will be going to a bowl game this year.
Matching—An elegant professionally done matching system would probably require client-side programming possibly in conjunction with the HTML5 canvas element. You could create a rather awkward
