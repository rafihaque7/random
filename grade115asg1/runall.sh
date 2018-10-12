for i in *.pdf; do
    echo "The file is " $i
    python rca.py $i

done