# MediaNav
## Data analysis checkout for development
The data analysis module uses [conda](https://www.anaconda.com/download/) as a dependency management tool. I would advise using the commandline tool for most of the stuff since the GUI is somewhat lacking when it comes to the search options, and is quite slow.

Checkout steps(for Dataspell):
- Clone the repository to the desired folder
- Open a conda prompt in the root directory of the project and run `conda env create -f .\analysis\environment.yml`
- You can verify the environment installation with `conda env list`, `medianav` should be listed among the existing environments.
- Open up Dataspell and import the project (File > Open), and add the python.exe from the installed environment as a new local interpreter. (Right click on project > Interpreter > Add local interpreter > Conda environment > New interpreter "..." button > search for the python.exe file)
- Your environment should be ready to go!
