package source;
import java.util.Iterator;
import exceptions.BoundaryViolationException;
import exceptions.EmptyTreeException;
import exceptions.InvalidPositionException;
import exceptions.NonEmptyTreeException;
import position.Position;
import tad_lista_de_nodos.NodePositionList;
import tad_lista_de_nodos.PositionList;


public class LinkedTree<E> implements Tree<E>  {
	
	protected TreePosition<E> root;
	protected int size;

	
	public LinkedTree() {
	root = null;
	size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() { return (size == 0); }
	
	public boolean isInternal(Position<E> v) throws InvalidPositionException { return !isExternal(v); }
	
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
	TreePosition<E> vv = checkPosition(v);
	return (vv.getChildren() == null) || vv.getChildren().isEmpty();
	}
	
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		return (v == root());
		}
		
		public TreePosition<E> root() throws EmptyTreeException {
		if (root == null) throw new EmptyTreeException("The tree is empty");
		return root;
		}
		
		public TreePosition<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TreePosition<E> vv = checkPosition(v);
		TreePosition<E> parentPos = vv.getParent();
		if (parentPos == null) throw new BoundaryViolationException("No parent");
		return parentPos;
		}
		
		public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TreePosition<E> vv = checkPosition(v);
		return vv.getChildren();
		}
		
		public Iterable<Position<E>> positions() {
		PositionList<Position<E>> positions = new NodePositionList<Position<E>>();
		if (size != 0) preorderPositions(root(), positions);
		return positions;
		}
		
		public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		PositionList<E> elements = new NodePositionList<E>();
		for (Position<E> pos : positions) elements.addLast(pos.element());
		return elements.iterator();
		}
		
		public E replace(Position<E> v, E o) throws InvalidPositionException {
			TreePosition<E> vv = checkPosition(v);
			E temp = v.element();
			vv.setElement(o);
			return temp;
		}

		public TreePosition<E> addRoot(E e) throws NonEmptyTreeException {
			if (!isEmpty()) throw new NonEmptyTreeException("Tree already has a root");
			size = 1;
			root = createNode(e, null, null);
			return root;
		}

		public void swapElements(Position<E> v, Position<E> w) throws InvalidPositionException {
			TreePosition<E> vv = checkPosition(v);
			TreePosition<E> ww = checkPosition(w);
			E temp = w.element();
			ww.setElement(v.element());
			vv.setElement(temp);
		}

		protected TreePosition<E> checkPosition(Position<E> v) throws InvalidPositionException {
			if (v == null || !(v instanceof TreePosition)) throw new InvalidPositionException("The position is invalid");
			return (TreePosition<E>) v;
		}
		
		protected TreePosition<E> createNode(E element, TreePosition<E> parent, PositionList<Position<E>> children) {
			return new TreeNode<E>(element, parent, children);
		}
		
		protected void preorderPositions(Position<E> v, PositionList<Position<E>> pos) throws InvalidPositionException {
			pos.addLast(v);
			for (Position<E> w : children(v)) preorderPositions(w, pos);
		}
		public String toString() { return toString(this); }
		public static <E> String toString(LinkedTree<E> T) {
			String s = "";
			for (E i : T) { s += ", " + i; }

			s = (s.length() == 0 ? s : s.substring(2));
			return "[" + s + "]";
		}
}

